package com.waben.stock.datalayer.organization.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.datalayer.organization.business.BindCardBusiness;
import com.waben.stock.datalayer.organization.entity.Organization;
import com.waben.stock.datalayer.organization.repository.DynamicQuerySqlDao;
import com.waben.stock.datalayer.organization.repository.OrganizationDao;
import com.waben.stock.datalayer.organization.repository.impl.MethodDesc;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.organization.AdminAgentDetailDto;
import com.waben.stock.interfaces.dto.organization.OrganizationDetailDto;
import com.waben.stock.interfaces.dto.organization.TreeNode;
import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.enums.BindCardResourceType;
import com.waben.stock.interfaces.enums.OrganizationState;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.form.organization.OrganizationForm;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.util.StringUtil;

/**
 * 机构 Service
 *
 * @author luomengan
 */
@Service
public class OrganizationService {

	@Autowired
	private OrganizationDao organizationDao;

	@Autowired
	private BindCardBusiness bindCardBusiness;

	@Autowired
	private DynamicQuerySqlDao dynamicQuerySqlDao;

	@Autowired
	private DynamicQuerySqlDao sqlDao;

	@Autowired
	public OrganizationService organizationService;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public Organization getOrganizationInfo(Long id) {
		return organizationDao.retrieve(id);
	}

	@Transactional
	public Organization addOrganization(Organization organization) {
		return organizationDao.create(organization);
	}

	@Transactional
	public Organization addOrganization(OrganizationForm orgForm) {
		Organization parent = organizationDao.retrieve(orgForm.getParentId());
		int level = 1;
		if (parent == null) {
			throw new ServiceException(ExceptionConstant.ORGANIZATION_NOTEXIST_EXCEPTION);
		} else {
			level = parent.getLevel() + 1;
		}
		// 判断机构名称是否存在
		List<Organization> checkName = organizationDao.retriveByName(orgForm.getName());
		if (checkName != null && checkName.size() > 0) {
			throw new ServiceException(ExceptionConstant.ORGNAME_EXIST_EXCEPTION);
		}
		// 生成机构代码
		List<Organization> childList = organizationDao.listByParentOrderByCodeDesc(parent);
		String code = parent.getCode();
		if (childList != null && childList.size() > 0) {
			Organization max = childList.get(0);
			String suffix = max.getCode().substring(code.length());
			Long seria = Long.parseLong(suffix) + 1;
			String seriaStr = seria.toString();
			if (seriaStr.length() < suffix.length()) {
				int lack = suffix.length() - seriaStr.length();
				for (int i = 0; i < lack; i++) {
					seriaStr = "0" + seriaStr;
				}
			}
			code += seriaStr;
		} else {
			code += "001";
		}
		// 保存机构
		Organization org = new Organization();
		org.setCode(code);
		org.setCreateTime(new Date());
		org.setLevel(level);
		org.setName(orgForm.getName());
		org.setParent(parent);
		org.setRemark(orgForm.getRemark());
		org.setState(OrganizationState.NORMAL);
		return organizationDao.create(org);
	}

	@Transactional
	public Organization modifyOrganization(Organization organization) {
		return organizationDao.update(organization);
	}

	@Transactional
	public void deleteOrganization(Long id) {
		organizationDao.delete(id);
	}

	@Transactional
	public void deleteOrganizations(String ids) {
		if (ids != null) {
			String[] idArr = ids.split(",");
			for (String id : idArr) {
				if (!"".equals(id.trim())) {
					organizationDao.delete(Long.parseLong(id.trim()));
				}
			}
		}
	}

	public Page<Organization> organizations(int page, int limit) {
		return organizationDao.page(page, limit);
	}

	public List<Organization> list() {
		return organizationDao.list();
	}

	public Page<Organization> pagesByQuery(final OrganizationQuery query) {
		Pageable pageable = new PageRequest(query.getPage(), query.getSize());
		Page<Organization> pages = organizationDao.page(new Specification<Organization>() {
			@Override
			public Predicate toPredicate(Root<Organization> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<>();
				if (query.isOnlyLoginOrg()) {
					predicateList.add(criteriaBuilder.equal(root.get("id").as(Long.class), query.getLoginOrgId()));
				} else {
					if (query.getCode() != null && !"".equals(query.getCode().trim())) {
						predicateList.add(
								criteriaBuilder.like(root.get("code").as(String.class), "%" + query.getCode() + "%"));
					}
					if (query.getState() != null && !"".equals(query.getState().trim())
							&& !"0".equals(query.getState().trim())) {
						predicateList.add(criteriaBuilder.equal(root.get("state").as(OrganizationState.class),
								OrganizationState.getByIndex(query.getState().trim())));
					}
					if (query.getParentId() != null && query.getParentId() != 0) {
						Join<Organization, Organization> parentJoin = root.join("parent", JoinType.LEFT);
						predicateList
								.add(criteriaBuilder.equal(parentJoin.get("id").as(Long.class), query.getParentId()));
					}
				}
				if (predicateList.size() > 0) {
					criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
				}
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("level").as(Integer.class)),
						criteriaBuilder.asc(root.get("createTime").as(Date.class)));
				return criteriaQuery.getRestriction();
			}
		}, pageable);
		return pages;
	}

	public Page<Organization> pagesByOrgQuery(final OrganizationQuery query) {
		Pageable pageable = new PageRequest(query.getPage(), query.getSize());
		Page<Organization> pages = organizationDao.page(new Specification<Organization>() {
			@Override
			public Predicate toPredicate(Root<Organization> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<>();
				if (query.isOnlyLoginOrg()) {
					predicateList.add(criteriaBuilder.equal(root.get("id").as(Long.class), query.getLoginOrgId()));
				}
				if (query.getCode() != null && !"".equals(query.getCode().trim())) {
					predicateList
							.add(criteriaBuilder.like(root.get("code").as(String.class), "%" + query.getCode() + "%"));
				}
				if (!StringUtil.isEmpty(query.getOrganizationName())) {
					predicateList.add(criteriaBuilder.like(root.get("name").as(String.class),
							"%" + query.getOrganizationName() + "%"));
				}
				if (query.getState() != null && !"".equals(query.getState().trim())
						&& !"0".equals(query.getState().trim())) {
					predicateList.add(criteriaBuilder.equal(root.get("state").as(OrganizationState.class),
							OrganizationState.getByIndex(query.getState().trim())));
				}
				if (query.getParentId() != null && query.getParentId() != 0) {
					Join<Organization, Organization> parentJoin = root.join("parent", JoinType.LEFT);
					predicateList.add(criteriaBuilder.equal(parentJoin.get("id").as(Long.class), query.getParentId()));
				}
				if (predicateList.size() > 0) {
					criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
				}
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("level").as(Integer.class)),
						criteriaBuilder.asc(root.get("createTime").as(Date.class)));
				return criteriaQuery.getRestriction();
			}
		}, pageable);
		return pages;
	}

	public List<Organization> listByParentId(Long parentId) {
		Organization parent = null;
		if (parentId.longValue() > 0) {
			parent = organizationDao.retrieve(parentId);
			if (parent == null) {
				return new ArrayList<Organization>();
			}
		}
		return organizationDao.listByParent(parent);
	}

	public List<TreeNode> adminTree(Long orgId) {
		List<TreeNode> result = new ArrayList<>();
		// // 根节点添加
		// TreeNode root = new TreeNode();
		// root.setId(0L);
		// root.setLevel(0);
		// root.setName("所有机构");
		// root.setOpen(true);
		// root.setPid(-1L);
		// result.add(root);
		// 添加所有数据栏目
		Organization org = null;
		if (orgId != null) {
			// org = organizationDao.retrieve(orgId);
			// result.add(new TreeNode(org.getId(), org.getName(),
			// org.getLevel(), 0L, true));
			// result.addAll(loopTree(org));
			org = organizationDao.retrieve(orgId);
			TreeNode node = new TreeNode(org.getId(), org.getName(), org.getLevel(), 0L, true);
			wrapTreeNode(node);
			result.add(node);
		}
		return result;
	}

	private void wrapTreeNode(TreeNode node) {
		List<TreeNode> children = childrenTreeNode(node.getId());
		node.setChildren(children);
		if (children != null && children.size() > 0) {
			for (TreeNode innerNode : children) {
				wrapTreeNode(innerNode);
			}
		}
	}

	private List<TreeNode> childrenTreeNode(Long orgId) {
		List<TreeNode> result = new ArrayList<>();
		Organization parent = organizationDao.retrieve(orgId);
		List<Organization> childList = organizationDao.listByParent(parent);
		if (childList != null && childList.size() > 0) {
			for (Organization org : childList) {
				result.add(new TreeNode(org.getId(), org.getName(), org.getLevel(),
						org.getParent() != null ? org.getParent().getId() : 0, false));
			}
		}
		return result;
	}

	// private List<TreeNode> loopTree(Organization parent) {
	// List<TreeNode> result = new ArrayList<>();
	// List<Organization> childList = organizationDao.listByParent(parent);
	// if (childList != null && childList.size() > 0) {
	// for (Organization child : childList) {
	// // 添加当前栏目
	// result.add(new TreeNode(child.getId(), child.getName(), child.getLevel(),
	// child.getParent() != null ? child.getParent().getId() : 0, false));
	// // 添加子栏目
	// result.addAll(loopTree(child));
	// }
	// }
	// return result;
	// }

	public OrganizationDetailDto detail(Long orgId) {
		Organization org = organizationDao.retrieve(orgId);
		if (org != null) {
			OrganizationDetailDto result = CopyBeanUtils.copyBeanProperties(OrganizationDetailDto.class, org, false);

			String sql = "select count(*) from publisher t1 INNER JOIN p_organization_publisher t2 on t1.id=t2.publisher_id and t2.org_code like '"
					+ org.getCode() + "%'";

			if (org.getLevel() == 1) {// 获取一级代理下的代理商个数
				String psql = "SELECT COUNT(*) FROM p_organization p where p.`code` LIKE '01%' and LENGTH(p.`code`) > 2";
				BigInteger size = dynamicQuerySqlDao.executeComputeSql(psql);
				result.setChildOrgCount(size != null ? size.intValue() : 0);
			} else {
				result.setChildOrgCount(childrenTreeNode(orgId).size());
				int codeLength = org.getCode().length();
				sql += " AND length(t2.org_code) <=" + (codeLength + 3) + "";
			}

			BindCardDto bindDao = organizationService.getBindCard(orgId);
			if (bindDao != null) {
				result.setAgnetName(bindDao.getName());
				result.setPhone(bindDao.getPhone());
			}
			if (org.getAccount() != null) {
				result.setBalance(org.getAccount().getBalance());
			}
			BigInteger totalElements = dynamicQuerySqlDao.executeComputeSql(sql);
			result.setPublisherCount(totalElements != null ? totalElements.intValue() : 0);
			return result;
		}
		return null;
	}

	public Organization modifyName(Long id, String name) {
		Organization org = organizationDao.retrieve(id);
		org.setName(name);
		return organizationDao.update(org);
	}

	public BindCardDto getBindCard(Long orgId) {
		List<BindCardDto> bindCardList = bindCardBusiness.listsByOrgId(orgId);
		if (bindCardList != null && bindCardList.size() > 0) {
			return bindCardList.get(bindCardList.size() - 1);
		}
		return null;
	}

	public BindCardDto bindCard(Long orgId, BindCardDto bindCardDto) {
		if (bindCardDto.getId() != null && bindCardDto.getId() > 0) {
			if (bindCardDto.getResourceType() == BindCardResourceType.ORGANIZATION
					&& bindCardDto.getResourceId().longValue() == orgId.longValue()) {
				return bindCardBusiness.revision(bindCardDto);
			} else {
				throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION, "错误的绑卡信息!");
			}
		} else {
			bindCardDto.setResourceType(BindCardResourceType.ORGANIZATION);
			bindCardDto.setResourceId(orgId);
			return bindCardBusiness.save(bindCardDto);
		}
	}

	public Organization findByCode(String code) {
		return organizationDao.retrieveByCode(code);
	}

	public Page<Organization> pagesAgentByQuery(final OrganizationQuery query) {
		Pageable pageable = new PageRequest(query.getPage(), query.getSize());
		Page<Organization> pages = organizationDao.page(new Specification<Organization>() {
			@Override
			public Predicate toPredicate(Root<Organization> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<>();
				if (query.isOnlyLoginOrg()) {
					predicateList.add(criteriaBuilder.equal(root.get("id").as(Long.class), query.getLoginOrgId()));
				} else {
					if (query.getCode() != null && !"".equals(query.getCode().trim())) {
						predicateList.add(
								criteriaBuilder.like(root.get("code").as(String.class), "%" + query.getCode() + "%"));
					}
					if (query.getState() != null && !"".equals(query.getState().trim())
							&& !"0".equals(query.getState().trim())) {
						predicateList.add(criteriaBuilder.equal(root.get("state").as(OrganizationState.class),
								OrganizationState.getByIndex(query.getState().trim())));
					}
					if (query.getParentId() != null && query.getParentId() != 0) {
						Join<Organization, Organization> parentJoin = root.join("parent", JoinType.LEFT);
						predicateList
								.add(criteriaBuilder.equal(parentJoin.get("id").as(Long.class), query.getParentId()));
					}
				}
				if (predicateList.size() > 0) {
					criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
				}
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("level").as(Integer.class)),
						criteriaBuilder.asc(root.get("createTime").as(Date.class)));
				return criteriaQuery.getRestriction();
			}
		}, pageable);
		return pages;
	}

	public Page<AdminAgentDetailDto> adminPagesByQuery(OrganizationQuery query) {
		String nameCondition = "";
		// if (!StringUtil.isEmpty(query.getOrganizationName())) {
		// nameCondition = " and t2.name like '%" + query.getOrganizationName()
		// + "%' ";
		// }
		String sql = String
				.format("SELECT p.id,p.`code`,p.`name`,p.`level`, p1.balance, p.create_time,p2.`name` as agentName,p2.phone from p_organization AS p INNER JOIN p_organization_account p1 ON p.id = p1.org_id"
						+ " LEFT JOIN bind_card p2 ON p2.resource_type = 2 AND p2.resource_id = p.id"
						+ " where 1=1 %s order by p.create_time desc limit " + query.getPage() * query.getSize() + ","
						+ query.getSize(), nameCondition);
		String countSql = "select count(*) " + sql.substring(sql.indexOf("from"), sql.indexOf("limit"));
		Map<Integer, MethodDesc> setMethodMap = new HashMap<>();
		setMethodMap.put(new Integer(0), new MethodDesc("setId", new Class<?>[] { Long.class }));
		setMethodMap.put(new Integer(1), new MethodDesc("setCode", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(2), new MethodDesc("setName", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(3), new MethodDesc("setLevel", new Class<?>[] { Integer.class }));
		setMethodMap.put(new Integer(4), new MethodDesc("setBalance", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(5), new MethodDesc("setCreateTime", new Class<?>[] { Date.class }));
		setMethodMap.put(new Integer(6), new MethodDesc("setName", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(7), new MethodDesc("setPhone", new Class<?>[] { String.class }));
		List<AdminAgentDetailDto> content = sqlDao.execute(AdminAgentDetailDto.class, sql, setMethodMap);
		BigInteger totalElements = sqlDao.executeComputeSql(countSql);
		return new PageImpl<>(content, new PageRequest(query.getPage(), query.getSize()),
				totalElements != null ? totalElements.longValue() : 0);
	}

}

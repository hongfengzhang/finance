package com.waben.stock.applayer.tactics.business;

import com.waben.stock.applayer.tactics.reference.*;
import com.waben.stock.interfaces.dto.activity.*;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityBusiness {

    @Autowired
    @Qualifier("activityFeignService")
    private ActivityReference activityReference;

    @Autowired
    @Qualifier("publisherDeduTicketFeignService")
    private PublisherDeduTicketReference publisherDeduTicketReference;

    @Autowired
    @Qualifier("publisherTeleChargeFeignService")
    private PublisherTeleChargeReference publisherTeleChargeReference;

    @Autowired
    @Qualifier("publisherTicketFeignService")
    private PublisherTicketReference publisherTicketReference;

    @Autowired
    @Qualifier("activityPublisherFeignService")
    private ActivityPublisherReference activityPublisherReference;

    @Autowired
    private PublisherBusiness publisherBusiness;

    public ActivityDto findActivityById(long activityId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<Map<String,String>> listWinning = new ArrayList<>();

        ActivityDto activityDto = activityReference.getActivity(activityId).getResult();

        List<ActivityPublisherDto> activityPublisherDtos = activityPublisherReference.getActivityPublisherByActivityId(activityId).getResult();

        for (ActivityPublisherDto activityPublisherDto : activityPublisherDtos) {
            long apId = activityPublisherDto.getApId();
            Response<PublisherDeduTicketDto> publisherDeduTicket = publisherDeduTicketReference.getPublisherDeduTicketByApId(apId);
            if(publisherDeduTicket.getCode().equals("200")&&publisherDeduTicket.getResult()!=null) {
                PublisherDto publisherDto = publisherBusiness.findById(publisherDeduTicket.getResult().getPubliserId());
                Map<String,String> mapPDT = new HashMap<>();
                mapPDT.put("winningTime",sdf.format(activityPublisherDto.getGetawardTime()));
                mapPDT.put("publisherPhone",publisherDto.getPhone());
                mapPDT.put("award",publisherDeduTicket.getResult().getMemo());
                listWinning.add(mapPDT);
            }

            Response<PublisherTeleChargeDto> publisherTeleCharge = publisherTeleChargeReference.getPublisherTeleChargeByApId(apId);
            if(publisherTeleCharge.getCode().equals("200")&&publisherTeleCharge.getResult()!=null) {
                PublisherDto publisherDto = publisherBusiness.findById(publisherTeleCharge.getResult().getPubliserId());
                Map<String,String> mapPTC = new HashMap<>();
                mapPTC.put("winningTime",sdf.format(activityPublisherDto.getGetawardTime()));
                mapPTC.put("publisherPhone",publisherDto.getPhone());
                mapPTC.put("award",publisherTeleCharge.getResult().getMemo());
                listWinning.add(mapPTC);
            }

            Response<PublisherTicketDto> publisherTicket = publisherTicketReference.getPublisherTicketByApId(apId);
            if(publisherTicket.getCode().equals("200")&&publisherTicket.getResult()!=null) {
                PublisherDto publisherDto = publisherBusiness.findById(publisherTicket.getResult().getPublisherId());
                Map<String,String> mapPT = new HashMap<>();
                mapPT.put("winningTime",sdf.format(activityPublisherDto.getGetawardTime()));
                mapPT.put("publisherPhone",publisherDto.getPhone());
                mapPT.put("award",publisherTeleCharge.getResult().getMemo());
                listWinning.add(mapPT);
            }
        }
        activityDto.setListWinningInfo(listWinning);
        return activityDto;
    }

}

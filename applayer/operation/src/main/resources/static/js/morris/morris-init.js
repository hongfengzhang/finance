
var InitiateLineChart2 = function () {
    return {
        init: function () {
            Morris.Line({
                element: 'line-chart-2',
                data: [
                  { x: '01月', a: 100,b: 90 },
                  { x: '02月', a: 75, b: 65 },
                  { x: '03月', a: 50, b: 40 },
                  { x: '04月', a: 75, b: 65 },
                  { x: '05月', a: 50, b: 40 },
                  { x: '06月', a: 75, b: 65 },
                  { x: '07月', a: 200, b: 90 },
                  { x: '08月', a: 200, b: 90 },
                  { x: '09月', a: 200, b: 90 },
                  { x: '10月', a: 200, b: 90 },
                  { x: '11月', a: 200, b: 90 },
                  { x: '12月', a: 200, b: 90 }
                ],
                xkey: 'x',
                ykeys: ['a', 'b'],
                labels: ['配资', '期权'],
                lineColors: ["green", "blue"]
            });
        }
    };
}();

var InitiateLineChart1 = function () {
    return {
        init: function () {
            Morris.Line({
                element: 'line-chart-1',
                data: [
                    { x: '01月', a: 100,b: 90 },
                    { x: '02月', a: 75, b: 65 },
                    { x: '03月', a: 50, b: 40 },
                    { x: '04月', a: 75, b: 65 },
                    { x: '05月', a: 50, b: 40 },
                    { x: '06月', a: 75, b: 65 },
                    { x: '07月', a: 200, b: 90 },
                    { x: '08月', a: 200, b: 90 },
                    { x: '09月', a: 200, b: 90 },
                    { x: '10月', a: 200, b: 90 },
                    { x: '11月', a: 200, b: 90 },
                    { x: '12月', a: 200, b: 90 }
                ],
                xkey: 'x',
                ykeys: ['a', 'b'],
                labels: ['配资', '期权'],
                lineColors: ["green", "blue"]
            });
        }
    };
}();

var InitiateDonutChart1 = function () {
    return {
        init: function () {
            Morris.Donut({
                element: 'donut-chart1',
                data: [
                    { label: '配资', value: 40 , },
                    { label: '期权', value: 5 }
                ],
                colors: ["green", "blue"],
                formatter: function (y) { return y + "%" }
            });
        }
    };
}();
var InitiateDonutChart2 = function () {
    return {
        init: function () {
            Morris.Donut({
                element: 'donut-chart2',
                data: [
                    { label: '配资', value: 40 },
                    { label: '期权', value: 5 }
                ],
                colors: ["green", "blue"],
                formatter: function (y) { return y + "%" }
            });
        }
    };
}();




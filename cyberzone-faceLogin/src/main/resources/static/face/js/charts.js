var myChart = echarts.init(document.getElementById('chart1'));

var dataAxis = ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月'];
var data = [60, 112, 121, 34, 90, 30, 10, 123];
var yMax = 200;
var dataShadow = [];

for (var i = 0; i < data.length; i++) {
    dataShadow.push(yMax);
}

option = {
    title: {
        text: '靶标数量',
        textStyle:{
        	color: '#7bfcfa',
        	fontSize:16
        }
    },
    xAxis: {
        data: dataAxis,
        axisLabel: {
            textStyle: {
                color: '#ddd',
            }
        },
        axisTick: {
            show: false
        },
        axisLine: {
            show: true,
            lineStyle:{
            	color:'rgba(83,189,250,0.3)'
            }
        },
        z: 10
    },
    yAxis: {
    	show:true,
        axisLine: {
            show: false
        },
        axisTick: {
            show: false
        },
        splitLine:{
        	show:false
        },
        axisLabel: {
            textStyle: {
                color: '#ddd'
            }
        }
    },
    series: [
        { // For shadow
            type: 'bar',
            itemStyle: {
                normal: {color: 'rgba(126,239,252,0.1)'}
            },
            barWidth:'15',
            barGap:'-100%',
            barCategoryGap:'40%',
            data: dataShadow,
            animation: false
        },
        {
            type: 'bar',
            barWidth:'15',
            itemStyle: {
                normal: {
                    color: new echarts.graphic.LinearGradient(
                        0, 0, 0, 1,
                        [
                            {offset: 0, color: '#25fff7'},
                            {offset: 0.35, color: '#36e0fb'},
                            {offset: 1, color: '#46c0ff'}
                        ]
                    )
                }
               
            },
            data: data
        }
    ]
};
myChart.setOption(option);



var myChart = echarts.init(document.getElementById('chart2'));
option2 = {
    angleAxis: {
        min:0,
        max:8,
        clockwise:false,
        axisLine:{
            show:false,
            lineStyle:{
                color:'transparent',
            }
        },
        axisTick:{
            show:false,
            lineStyle:{
                color:'transparent',
            }
        },
        splitLine:{
            show:false
        }
        
    },
    radiusAxis: {
        type: 'category',
        data: ['1', '2', '3', '4','5'],
        z: 10,
        boundaryGap: true,
         axisLine:{
            show:false,
            lineStyle:{
                color:'transparent',
            }
        },
        splitArea:{
            show:true,
            areaStyle:{
                color:['rgba(250,250,250,0)','rgba(200,200,200,0)','rgba(255,255,255,0.1)','rgba(255,255,255,0.1)','rgba(255,255,255,0.1)'] 
            },
            z: -1
        }
    },
    polar: {
    },
    series: [{
        type: 'bar',
        data: ['','', '', '', 4],
        itemStyle:{
        	color:'#25fff7'
        },
        coordinateSystem: 'polar',
        name: 'A',
        stack: 'a'
    }, {
        type: 'bar',
        data: ['','', '', 6, ''],
        coordinateSystem: 'polar',
        itemStyle:{
        	color:'#45c1ff'
        },
        name: 'B',
        stack: 'a'
    }, {
        type: 'bar',
        data: ['','', 2, '', ''],
        coordinateSystem: 'polar',
        itemStyle:{
        	color:'#fff003'
        },
        name: 'C',
        stack: 'a',
        barCategoryGap: '0%',
    }]
};
myChart.setOption(option2);


var myChart = echarts.init(document.getElementById('chart3'));
option3 = {
    series: [
        {
            name:'访问来源',
            type:'pie',
            radius: ['50%', '80%'],
            animation: false,
            avoidLabelOverlap: false,
            hoverAnimation:false,
            itemStyle:{

            },
            label: {
                normal: {
                    show: false,
                    position: 'center'
                }
            },
            data:[
                {
                	value:335, 
                	name:'直接访问',
                	itemStyle:{
            			color:'#26fff7'
            		},
                },
                 {
                	value:335, 
                	name:'直接访问',
                	itemStyle:{
            			color:'#5696bb'
            		},
                },
                 {
                	value:335, 
                	name:'直接访问',
                	itemStyle:{
            			color:'#45c1ff'
            		},
                },
                 {
                	value:335, 
                	name:'直接访问',
                	itemStyle:{
            			color:'#fff003'
            		},
                }
            ]
        }
    ]
};
myChart.setOption(option3);
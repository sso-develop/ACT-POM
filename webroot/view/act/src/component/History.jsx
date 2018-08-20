import React, { Component } from 'react';
import { Table,Form,Modal,Icon,message,Divider} from 'antd';
import $ from 'jquery';

class Instance extends Component {

	constructor(props) {
    	super(props);
    	this.state = {
               dataSource:[],
               visible:false,
               searchData:{
               	
               },
               pagination:{
                    total:0,
                    pageSize:0,
                    loading:false
                }
            }
    	 this.getHistoricActivityInstance()
    }
    getHistoricActivityInstance(){
		let that = this;
		$.post("/findHistoricActivityInstanceByPager.json", this.state.searchData,function(data) {
		     if(!data.success){
                message.error(data.msg)
             }else{
             	let pagination = that.state.pagination;
             	pagination.total = data.data.totalCount;
                pagination.current = data.data.pageNumber;
                pagination.pageSize = data.data.pageSize;
                that.setState({dataSource:data.data.result,pagination:pagination})
             }
		});
	}
	historicActivityInfo(record) {
	   var variables = record.variables;
	   var content = "<ul>"
	   for(var i = 0;i<variables.length;i++){
	     content +=" <li>"+variables[i].variableName+"："+variables[i].value+"</li>"
	   }
	   content += "</ul>"
      Modal.info({
        title: '流程参数设置',
        content: (
            <div dangerouslySetInnerHTML={{__html: content}}></div>
        ),
        onOk() {},
      });
    }
    commentsInfo(record){
     var comments = record.comments;
    	   var content = "<ul>"
    	   for(var i = 0;i<comments.length;i++){
    	     content +=" <li>"+comments[i].fullMessage+"</li>"
    	   }
    	   content += "</ul>"
          Modal.info({
            title: '任务评论',
            content: (
                <div dangerouslySetInnerHTML={{__html: content}}></div>
            ),
            onOk() {},
          });
    }
    render() {
        const columns = [{
            title: 'ID',
            dataIndex: 'id',
            key: 'id',
        },{
              title: '任务ID',
              dataIndex: 'taskId',
              key: 'taskId',
          },{
            title: '任务名称',
            dataIndex: 'activityName',
            key: 'activityName',
        }, {
              title: '任务办理人',
              dataIndex: 'assignee',
              key: 'assignee',
          },{
              title: '开始时间',
              dataIndex: 'startTime',
              key: 'startTime',
          },{
              title: '结束时间',
              dataIndex: 'endTime',
              key: 'endTime',
          },{
              title: '操作',
              key: 'action',
              render: (text, record) => {
                return (
                       <span>
                            <a onClick = {this.historicActivityInfo.bind(this,record)}>任务参数</a>
                              <Divider type="vertical" />
                            <a onClick = {this.commentsInfo.bind(this,record)}>任务评论</a>
                       </span>
                     )
              }
        }];
        const slef = this;
        return(
            <div>
                <Table
                bordered
                loading = {this.state.pagination.loading}
                rowKey={record => record.id} 
                pagination={{
        					showQuickJumper: true,
        					current:this.state.searchData.currentPage,
		       				total:this.state.pagination.total,
		       				pageSize:this.state.pagination.pageSize,
		       				showTotal: function () {  //设置显示一共几条数据
					            return '共 ' + (slef.state.pagination.total) + ' 条数据'; 
					        },
					        onChange(current) {  //点击改变页数的选项时调用函数，current:将要跳转的页数
						          const data = slef.state.searchData;
						          data.currentPage = current;
						          slef.setState({
						          	searchData:data
						          })
						          slef.getHistoricActivityInstance(slef);
						       
						    },  
				     }}
                dataSource={this.state.dataSource} columns={columns} />
            </div>
        )
    }
}

const InstanceForm = Form.create()(Instance);
export default InstanceForm;
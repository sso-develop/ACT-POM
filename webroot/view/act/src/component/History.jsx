import React, { Component } from 'react';
import { Table,Form,Input,Button,Row,Col,Modal,Upload,Icon,message,Popconfirm,Divider} from 'antd';
import $ from 'jquery';

const FormItem = Form.Item;
class Instance extends Component {

	constructor(props) {
    	super(props);
    	this.state = {
               dataSource:[],
               visible:false,
               pager:{
                    total:0,
                    pageSize:0,
                    loading:false
                }
            }
    	 this.getHistoricActivityInstance()
    }
    getHistoricActivityInstance(){
		let that = this;
		$.post("/findHistoricActivityInstance.json", {},function(data) {
		     if(!data.success){
                message.error(data.msg)
             }else{
                that.setState({dataSource:data.data.result})
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
                var id = record.id;
                return (
                       <span>
                            <a onClick = {this.historicActivityInfo.bind(this,record)}>任务参数</a>
                              <Divider type="vertical" />
                            <a onClick = {this.commentsInfo.bind(this,record)}>任务评论</a>
                       </span>
                     )
              }
        }];
        return(
            <div>
                <Table
                bordered
                loading = {this.state.pager.loading}
                rowKey={record => record.id} dataSource={this.state.dataSource} columns={columns} />
            </div>
        )
    }
}

const InstanceForm = Form.create()(Instance);
export default InstanceForm;
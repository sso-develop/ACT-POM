import React, { Component } from 'react';
import { Table,Form,Input,Button,Row,Col,Modal,Upload,Icon,message,Popconfirm,Divider,
	Select	
} from 'antd';
import $ from 'jquery';

import InstanceApproval from './InstanceApproval.jsx';
import InstanceAssign from './InstanceAssign.jsx';

const FormItem = Form.Item;
const { TextArea } = Input;
class Instance extends Component {

	constructor(props) {
    	super(props);
    	this.state = {
               dataSource:[],
               approvalVisible:false,
               assignVisible:false,
               userData:[],
               completeTask:{
                id:"",
                variables:"",
                comment:"",
                processInstanceId:"",
                processDefinitionId:''
               },
               searchData:{
               	assignee:'',
               },
               pagination:{
                    total:0,
                    pageSize:0,
                    loading:false
                }
            }
    	 this.getPersonTask();
    	 this.getUser(this);
    }
    getPersonTask(){
		let that = this;
		let param = this.state.searchData;
		$.post("/findPersonTask.json", param,function(data) {
		     if(!data.success){
                message.error(data.msg)
             }else{
             	const pagination = that.state.pagination;
             	pagination.total = data.data.totalCount;
                pagination.current = data.data.pageNumber;
                pagination.pageSize = data.data.pageSize;
                that.setState({dataSource:data.data.result,pagination:pagination})
             }
		});
	}
	
	getUser(){
		let self = this;
		$.post("/queryAllUser.json", {},function(data) {
		     if(!data.success){
                message.error(data.msg)
            }else{
            	self.setState({userData:data.data})
            }
		});
	}
	claim(record){
		let self = this;
		var param = {
			taskId:record.id,
			userId:12
		}
		$.post("/taskClaim.json", param,function(data) {
		     if(!data.success){
                message.error(data.msg)
           }else{
            	self.getPersonTask(self);
            }
		});
	}
	showModal(record){
	    var completeTask = this.state.completeTask;
	    completeTask.id = record.id;
	   	completeTask.processInstanceId = record.processInstanceId;
	   	completeTask.processDefinitionId = record.processDefinitionId;
    	this.setState({approvalVisible: true,completeTask:completeTask});
    }
	showAssignModal(record){
		var completeTask = this.state.completeTask;
	    completeTask.id = record.id;
	   	completeTask.processInstanceId = record.processInstanceId;
	   	
    	this.setState({assignVisible: true,completeTask:completeTask});
    }
    traceprocess(record){
        console.log(record)
        window.open("/traceprocess.json?definitionId="+record.processDefinitionId+"&instanceId="+record.processInstanceId)
    }
	closeModal(){
    	this.setState({ 
    		approvalVisible: false,
    		assignVisible:false,
    		completeTask:{}
    	});
    }

	handleCancel(e){
    	this.closeModal(this);
    }
   
    handleSearchSelectChange(n,v){
    	let d = this.state.searchData;
		d[n] = v;
		this.setState({
			searchData:d
		})
    }
    formReset(){
    	this.setState({
    		searchData:{}
    	})
    }
    render() {
        const columns = [{
            title: '任务ID',
            dataIndex: 'id',
            key: 'id',
        }, {
            title: '任务办理人',
            dataIndex: 'assigneeName',
            key: 'assigneeName',
        }, {
            title: '任务名称',
            dataIndex: 'name',
            key: 'name',
        },{
              title: '任务创建时间',
              dataIndex: 'createTime',
              key: 'createTime',
          },{
              title: '流程实例ID',
              dataIndex: 'processInstanceId',
              key: 'processInstanceId',
          },{
              title: '操作',
              key: 'action',
              render: (text, record) => {
                var id = record.id;
                return (
                       <span>
                            <a onClick = {this.showModal.bind(this,record)}>审核</a>
                             <Divider type="vertical" />
                            <a onClick = {this.showAssignModal.bind(this,record)}>指派</a>
                       </span>
                     )
              }
        }];

          const formItemLayout = {
                labelCol: {
                  xs: { span: 24 },
                  sm: { span: 5 },
                },
                wrapperCol: {
                  xs: { span: 24 },
                  sm: { span: 16 },
                },
              };
       const tailFormItemLayout = {
            wrapperCol: {
              xs: {
                span: 24,
                offset: 0,
              },
              sm: {
                span: 16,
                offset: 8,
              },
            },
          };


         const props = {
            name: 'upFile',
            action: '/uploadworkflow.json',
            headers: {
              authorization: 'authorization-text',
            },
            onChange(info) {
              if (info.file.status !== 'uploading') {
                console.log(info.file, info.fileList);
              }
              if (info.file.status === 'done') {
                message.success(`${info.file.name} file uploaded successfully`);
              } else if (info.file.status === 'error') {
                message.error(`${info.file.name} file upload failed.`);
              }
            },
         };
         const { getFieldDecorator } = this.props.form;
         const self = this;
        return(
            <div>
            	<div style={{"height":"100px"}}>
	                 <Form className="ant-advanced-search-form" style={{"width": "auto"}}>
	                    <Row gutter={24}>
	                    	<Col sm={2}>
                     	 		 <Button type="primary" style={{float:'right'}}>
			                        +新建工单
			                    </Button>
	                     	 </Col>
	                         <Col sm={5} offset={2}>
	                     	 	<FormItem
                                    label="任务办理人"
                                    labelCol={{span: 8}}
                                    wrapperCol={{span: 16}}
                                >
	                     	 		<Select
			                            style={{width:280}}
			                            placeholder="任务办理人"
			                            value={this.state.searchData.assignee}
			                            onSelect={this.handleSearchSelectChange.bind(this,'assignee')}
			                           >
									      {
									      	this.state.userData.map((item, index) => {
									      		return  <Select.Option key = {index} value={item.id}>{item.lastName+" "+item.firstName}</Select.Option>
									      	})
									      }
									      
									</Select>
	                     	 	</FormItem>
	                     	 </Col>
	                     	 
	                         <Col  style={{textAlign: 'right'}}>
                                <Button type="primary" icon="search" onClick={this.getPersonTask.bind(this)}>搜索</Button>
                                <Button style={{marginLeft: 10}}  onClick={this.formReset.bind(this)}>重置</Button>
                            </Col>
	                    </Row>
	                  </Form>
                 </div>
                 <div>
                 	 <Table
                	bordered
                	loading = {this.state.pagination.loading}
                	rowKey={record => record.id} 
                	pagination={{
            					showQuickJumper: true,
            					current:self.state.searchData.currentPage,
			       				total:this.state.pagination.total,
			       				pageSize:this.state.pagination.pageSize,
			       				showTotal: function () {  //设置显示一共几条数据
						            return '共 ' + (self.state.pagination.total) + ' 条数据'; 
						        },
						        onChange(current) {  //点击改变页数的选项时调用函数，current:将要跳转的页数
							          const data = self.state.searchData;
							          data.currentPage = current;
							          self.setState({
							          	searchData:data
							          })
							          self.getPersonTask(self);
							       
							    },  
					     }}
                	dataSource={this.state.dataSource} columns={columns} />
                 </div>
               
                <Modal
                	width='1500px'
                      title="任务审批"
                       visible={this.state.approvalVisible}
                       onCancel={this.handleCancel.bind(this)}
                       footer={null}
                    >
                     <InstanceApproval
                     	completeTask = {this.state.completeTask}
                     	onCloseModal = {this.closeModal.bind(this)}
                     	onLoadListData = {this.getPersonTask.bind(this)}
                     />
                 </Modal>
                 <Modal
                	width='800px'
                      title="任务指派"
                       visible={this.state.assignVisible}
                       footer={null}
                      onCancel={this.handleCancel.bind(this)}
                    >
                   <InstanceAssign 
                   	task = {this.state.completeTask}
                   	onCloseModal = {this.closeModal.bind(this)}
                    onLoadListData = {this.getPersonTask.bind(this)}
                    />
                 </Modal>
            </div>
        )
    }
}

const InstanceForm = Form.create()(Instance);
export default InstanceForm;
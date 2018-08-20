import React, { Component } from 'react';
import { Table,Form,Input,Button,Row,Col,Modal,Upload,Icon,message,Popconfirm,Divider,Tooltip,
	AutoComplete,
	Select
} from 'antd';
import $ from 'jquery';

const FormItem = Form.Item;
class User extends Component {

	constructor(props) {
    	super(props);
    	this.state = {
    		visible:false,
    		groupData:[],
    		user:{
    			firstName:'',
    			lastName:'',
    			email:'',
    			groupId:''
    		},
    		searchData:{
    			groupId:''
    		},
    		pagination:{
    			loading:false
    		}
    	}
    	this.loadListData(this);
    	this.getAllGroup(this);
    }
	closeModal(){
		this.setState({
			visible:false,
			group:{}
		})
	}
	formReset(){
		this.setState({searchData:{}});
	}
	showModal(){
		this.setState({visible:true})
	}
	handleCancel(){
		this.closeModal(this)
	}
	onHandleNameChange(n,v){
		let d = this.state.user;
		d[n] = v.target.value;
		this.setState({
			user:d
		})
	}
	handleSearchChange(n,v){
		Object.assign(this.state.user,{groupId:v})
	}
	handleSearchSelectChange(n,v){
		Object.assign(this.state.searchData,{groupId:v})
		this.setState({searchData:this.state.searchData});
	}
	saveUser(){
		let param = this.state.user;
		let slef = this;
		console.log()
		if(param.firstName === undefined || param.firstName === ''){
			message.error('用户名字不能空')
			return;
		}
		if(param.groupId === undefined || param.groupId === ''){
			message.error('请选择分组')
			return;
		}
		$.post("/saveUser.json", param,function(data) {
		     if(!data.success){
                message.error(data.msg)
            }else{
            	slef.closeModal(slef);
            	slef.loadListData(slef);
            }
		});
	}
	
	getAllGroup(){
		let self = this;
		$.post("/queryAllGroup.json", {},function(data) {
		    if(!data.success){
                message.error(data.msg)
            }else{
            	self.setState({groupData:data.data})
            }
		});
	}
	loadListData(){
		let param = this.state.searchData;
		let slef = this;
		Object.assign(this.state.pagination,{loading:true})
		$.post("/queryUserByPager.json", param,function(data) {
		     if(!data.success){
                message.error(data.msg)
            }else{
            	let pagination = slef.state.pagination;
             	pagination.total = data.data.totalCount;
                pagination.current = data.data.pageNumber;
                pagination.pageSize = data.data.pageSize;
                pagination.loading = false;
                slef.setState({dataSource:data.data.result,pagination:pagination})
            }
		});
	}
	 handleEmailChange = (value) => {
	    this.setState({
	      emailSource: !value || value.indexOf('@') >= 0 ? [] : [
	        `${value}@gmail.com`,
	        `${value}@163.com`,
	        `${value}@qq.com`,
	      ],
	    });
	    Object.assign(this.state.user,{email:value});
	  }
	 
	 deleteUser(record){
	 	let self = this;
	 	let params = {
	 		id:record.id
	 	}
	 	$.post("/deleteUser.json", params,function(data) {
		    if(!data.success){
                message.error(data.msg)
            }else{
           	 	self.loadListData(self);
            }
		});
	 }
	 
    render() {
    	const slef = this;
    	 const columns = [{
            title: 'ID',
            dataIndex: 'id',
            key: 'id',
        },{
            title: '用户名字',
            dataIndex: 'firstName',
            key: 'firstName',
        },{
            title: '用户姓氏',
            dataIndex: 'lastName',
            key: 'lastName',
           
        },{
            title: '用户邮箱',
            dataIndex: 'email',
            key: 'email',
           
        },{
              title: '操作',
              key: 'action',
              render: (text, record) => {
              	 var params = {
	                id: record.id
	            }
              	var title = '您是否确认删除该数据'
           		var content = <div>
                        <div>用户姓氏： {record.lastName}</div>
                    </div>;
                return (
                   <span>
                        <Popconfirm title='是否要删除?' onConfirm = {this.deleteUser.bind(this,record)} okText="确定" cancelText="取消">
                            <a>删除</a>
                        </Popconfirm>
                   </span>
                 )
         	}
        }]
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
        
        return(
            <div>
            	<div style={{"height":"100px"}}>
            		<Form  className="ant-advanced-search-form" style={{"width": "auto"}}>
	                    <Row gutter={24}>
	                    	<Col sm={2}>
                     	 		 <Button type="primary" style={{float:'right'}} onClick={this.showModal.bind(this)}>
			                        +添加成员
			                    </Button>
	                     	 </Col>
	                     	 
	                     	 <Col sm={5} offset={2}>
	                     	 	<FormItem
                                    label="分组标识"
                                    labelCol={{span: 8}}
                                    wrapperCol={{span: 16}}
                                >
	                     	 		<Select
			                            style={{width:280}}
			                            placeholder="请选择分组"
			                            value={this.state.searchData.groupId}
			                            onSelect={this.handleSearchSelectChange.bind(this,'group')}
			                           >
									      {
									      	this.state.groupData.map((item, index) => {
									      		return  <Select.Option key = {index} value={item.id}>{item.name}</Select.Option>
									      	})
									      }
									      
									</Select>
	                     	 	</FormItem>
	                     	 </Col>
	                     	 
	                         <Col  style={{textAlign: 'right'}}>
                                <Button type="primary" icon="search" onClick={this.loadListData.bind(this)}>搜索</Button>
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
            	<Modal
            		visible = {this.state.visible}
                	width='500px'
                    title="新增用户"
                    footer={null}
                     onCancel={this.handleCancel.bind(this)}
                    >
		            	<Form
			                horizontal = {'true'} 
				 			onSubmit={this.saveUser.bind(this)}
				 		>
		                    <FormItem
		                            {...formItemLayout}
		                            required
		                          label="用户名字"
		                     >
						 	<Input placeholder="用户名字" 
						 		onChange = {this.onHandleNameChange.bind(this,'firstName')}
						 		vaule = {this.state.user.firstName}/>
							 </FormItem>
							  <FormItem
		                           {...formItemLayout}
		                          label="用户姓氏"
		                     >
						 	<Input placeholder="用户姓氏" 
						 		onChange = {this.onHandleNameChange.bind(this,'lastName')}
						 		vaule = {this.state.user.lastName}/>
							 </FormItem>
							   <FormItem
		                           {...formItemLayout}
		                          label="用户邮箱"
		                     >
						 		 <AutoComplete
						            dataSource={this.state.emailSource}
						            onChange={this.handleEmailChange.bind(this)}
						            placeholder="Email"
						          />
							 </FormItem>
							 <FormItem
		                           {...formItemLayout}
		                           required
		                          label="用户分组"
		                     >
							 	<Select
			                            style={{width:280}}
			                            placeholder="请选择分组"
			                            onSelect={this.handleSearchChange.bind(this,'group')}
			                           >
									      {
									      	this.state.groupData.map((item, index) => {
									      		return  <Select.Option key = {index} value={item.id}>{item.name}</Select.Option>
									      	})
									      }
									      
									</Select>
							 </FormItem>
							  <div className="task-bar"></div>
			             	<FormItem labelCol={{span: 6}} style={{textAlign:"center"}} wrapperCol={{span: 14}}>
				                <Button type="primary"  htmlType="submit">提交</Button>
				                <span style={{padding:"0 3px"}}></span>
			             	</FormItem>
			            </Form>
                 </Modal>
            </div>
        )
    }
}
const UserForm = Form.create()(User);
export default UserForm;
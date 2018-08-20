import React, { Component } from 'react';
import { Table,Form,Input,Button,Row,Col,Modal,Upload,Icon,message,Popconfirm,Divider,Tooltip} from 'antd';
import $ from 'jquery';

const FormItem = Form.Item;
class Instance extends Component {

	constructor(props) {
    	super(props);
    	this.state = {
    		visible:false,
    		group:{
    			name:'',
    			type:''
    		},
    		searchData:{},
    		pagination:{
    			loading:false
    		}
    	}
    	this.loadListData(this);
    }
	closeModal(){
		this.setState({
			visible:false,
			group:{
    			name:'',
    			type:''
    		},
		})
	}
	showModal(){
		this.setState({visible:true})
	}
	handleCancel(){
		this.closeModal(this)
	}
	onHandleNameChange(n,v){
		console.log(n,v)
		let d = this.state.group;
		d[n] = v.target.value;
		this.setState({
			group:d
		})
	}
	
	saveGroup(){
		let param = this.state.group;
		let slef = this;
		$.post("/saveGroup.json", param,function(data) {
		     if(!data.success){
                message.error(data.msg)
            }else{
            	slef.closeModal(slef);
            	slef.loadListData(slef);
            }
		});
	}
	
	
	loadListData(){
		let param = {};
		let slef = this;
		Object.assign(this.state.pagination,{loading:true})
		$.post("/queryGroupByPager.json", param,function(data) {
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
	 deleteGroup(record){
	 	let self = this;
	 	let params = {
	 		id:record.id
	 	}
	 	$.post("/deleteGroup.json", params,function(data) {
		    if(!data.success){
                message.error(data.msg)
            }else{
           	 	self.loadListData(self);
            }
		});
	 }
    render() {
    	
    	 const columns = [{
            title: 'ID',
            dataIndex: 'id',
            key: 'id',
        },{
            title: '分组名称',
            dataIndex: 'name',
            key: 'name',
        },{
            title: '分组类型',
            dataIndex: 'type',
            key: 'type',
           
        },{
              title: '操作',
              key: 'action',
              render: (text, record) => {
                return (
                   <span>
                        <Popconfirm title='是否要删除?' onConfirm = {this.deleteGroup.bind(this,record)} okText="确定" cancelText="取消">
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
        const slef = this;
        return(
            <div>
            	<div style={{"height":"100px"}}>
            		<Form className="ant-advanced-search-form" style={{"width": "auto"}}>
	                    <Row gutter={24}>
	                        <Col sm={2}>
                     	 		 <Button type="primary" style={{float:'right'}} onClick={this.showModal.bind(this)}>
			                        +添加分组
			                    </Button>
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
                    title="新增"
                    footer={null}
                     onCancel={this.handleCancel.bind(this)}
                    >
		            	<Form
			                horizontal = {'true'} 
				 			onSubmit={this.saveGroup.bind(this)}
				 		>
		                    <FormItem
		                            {...formItemLayout}
		                          label="分组名称"
		                     >
						 	<Input placeholder="分组名称" 
						 		onChange = {this.onHandleNameChange.bind(this,'name')}
						 		vaule = {this.state.group.name}/>
							 </FormItem>
							  <FormItem
		                           {...formItemLayout}
		                          label="分组类型"
		                     >
						 	<Input placeholder="分组类型" 
						 		onChange = {this.onHandleNameChange.bind(this,'type')}
						 		vaule = {this.state.group.type}/>
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
const InstanceForm = Form.create()(Instance);
export default InstanceForm;
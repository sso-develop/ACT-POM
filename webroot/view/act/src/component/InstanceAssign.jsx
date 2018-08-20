import React, { Component } from 'react';
import { Table,Form,Input,Button,Row,Col,Modal,Upload,Icon,message,Popconfirm,Divider,
	Select	
} from 'antd';

import $ from 'jquery';

const FormItem = Form.Item;
class InstanceAssign extends Component {
	constructor(props) {
    	super(props);
    	this.state = {
    		task:this.props.task,
    		userData:[],
    		assign:{
    			taskId:this.props.task.id,
    			userId:''
    		}
    	}
    	this.getUser(this);
    }
	componentWillReceiveProps(nextProps){
		this.setState({
			task:nextProps.task,
			assign:{
    			taskId:nextProps.task.id,
    			userId:''
    		}
		});
	}
	handleSelectChange(n,v){
		let d = this.state.assign;
		d[n] = v;
		this.setState({
			assign:d
		})
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
	assignTask(){
		let slef = this;
		let param = this.state.assign;
		if(param.taskId === ''){
			 message.error('taskId不能为空')
			return;
		}
		if(param.userId === ''){
			 message.error('请选择任务办理人')
			return;
		}
		$.post("/taskAssign.json", param,function(data) {
		     if(!data.success){
                message.error(data.msg)
            }else{
            	slef.props.onCloseModal();
                slef.props.onLoadListData();
            }
		});
	}
	render() {
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
		return (
			<Form
                horizontal = {'true'} 
	 			onSubmit={this.assignTask.bind(this)}
	 		>
				 <FormItem
                           {...formItemLayout}
                           required
                          label="任务办理人"
                     >
				 	<Select
                        style={{width:280}}
                        placeholder="请选择分组"
                        onSelect={this.handleSelectChange.bind(this,'userId')}
                       >
					      {
					      	this.state.userData.map((item, index) => {
					      		return  <Select.Option key = {index} value={item.id}>{item.lastName+" "+item.firstName}</Select.Option>
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
		)
	}
}
InstanceAssign = Form.create()(InstanceAssign);
export default InstanceAssign;
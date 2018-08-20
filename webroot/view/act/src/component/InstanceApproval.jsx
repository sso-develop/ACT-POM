import React, { Component } from 'react';
import { Table,Form,Input,Button,Row,Col,Modal,Upload,Icon,message,Popconfirm,Divider,Tooltip,
	Radio,
	Select
} from 'antd';
import $ from 'jquery';

const FormItem = Form.Item;
const { TextArea } = Input;
const InputGroup = Input.Group;
const RadioGroup = Radio.Group;
class InstanceApproval extends Component {
	constructor(props) {
    	super(props);
    	this.state = {
            completeTask:this.props.completeTask,
            variableList:[],
            dataSource:[],
            paramType:''
        }
    	this.getHistoricActivityInstance(this.state.completeTask.processInstanceId);
    }
	componentWillReceiveProps(nextProps){
		this.getHistoricActivityInstance(this.state.completeTask.processInstanceId);
		this.setState({
			variableList:[],
			completeTask:nextProps.completeTask
		})
	}
	handleFormVariables(currency){
	    var completeTask = this.state.completeTask;
        completeTask.variables = currency.target.value;
	    this.state = {completeTask:completeTask}
	}
	 getHistoricActivityInstance(_processInstanceId){
		let that = this;
		$.post("/findHistoricActivityInstance.json", {processInstanceId:_processInstanceId},function(data) {
			console.log();
		     if(!data.success){
                message.error(data.msg)
             }else{
                that.setState({dataSource:data.data})
             }
		});
	}
	completeTask(e){
		e.preventDefault();
		const variables = {};
		this.state.variableList.map((item, index) => {
			variables[item.key] = item.value;
			return null;
		})
	    var completeTask = this.state.completeTask;
	    completeTask.variables = JSON.stringify(variables);
	    let that = this;
	    
        $.post("/completeTask.json", completeTask,function(data) {
            console.log(data);
             if(!data.success){
                message.error(data.msg)
              }else{
                that.props.onCloseModal();
                that.props.onLoadListData();
              }
		});
	}
	handleFormComment(comment){
	    var completeTask = this.state.completeTask;
        completeTask.comment = comment.target.value;
        this.state = {completeTask:completeTask}
        
        
	}
	/***********************/
	onChangeVariableKey(index,e){
		var arr = this.state.variableList;
		let a = arr[index];
		a.key = e.target.value;
		this.setState({variableList:arr});
	}
	onChangeVariableValue(index,e){
		var arr = this.state.variableList;
		let a = arr[index];
		a.value = e.target.value;
		this.setState({variableList:arr});
	}
	onChangeSelectVariableValue(index,v){
		var arr = this.state.variableList;
		let a = arr[index];
		a.value = v;
		this.setState({variableList:arr});
	}
	onPlusVariableItem(){
		if(this.state.paramType === ''){
			message.error('请选择参数类型')
			return
		}
		this.state.variableList.push({
			key:'',
            value:'',
            type:this.state.paramType
		})
		this.setState({variableList:this.state.variableList});
	}
	onMinusVariableItem(index,e){
        this.state.variableList.splice(index, 1);
        this.setState({variableList:this.state.variableList});
	}
	paramTypeChange(e){
		this.setState({paramType:e.target.value});
	}
	render() {
		const columns = [{
            title: '任务名称',
            dataIndex: 'activityName',
            key: 'activityName',
        }, {
              title: '任务办理人',
              dataIndex: 'assignee',
              key: 'assignee',
          },{
              title: '任务评论',
              dataIndex: 'comments',
              key: 'comments',
	          render: (text) => {
	            	let c = text.map((item, index) => {
	            		return item.fullMessage;
	            	})
            		return c;
            	}
          },{
              title: '任务参数',
              dataIndex: 'variables',
              key: 'variables',
	          render: (text) => {
	            	let c = text.map((item, index) => {
	            		return item.variableName+"："+item.value+"  ";
	            	})
            		return <div dangerouslySetInnerHTML={{__html: c}}></div>;
            	}
          },{
              title: '开始时间',
              dataIndex: 'startTime',
              key: 'startTime',
          },{
              title: '结束时间',
              dataIndex: 'endTime',
              key: 'endTime',
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
        const traceprocessUrl = "/traceprocess.json?definitionId="+this.state.completeTask.processDefinitionId+"&instanceId="+this.state.completeTask.processInstanceId
	 	return(
	 		<Form
                horizontal = {'true'} 
	 			onSubmit={this.completeTask.bind(this)}
	 		>
	 				<FormItem
                           {...formItemLayout}
                          label="进度图"
                     >
	 					<img src={traceprocessUrl}/>
	 				</FormItem>
                     <FormItem
                           {...formItemLayout}
                          label="任务参数"
                     >
                  	
                  	<InputGroup size="large">
			         <Col span={7}>
			         </Col>
					<Col span={10}>
							<RadioGroup 
								value={this.state.paramType}
								onChange={this.paramTypeChange.bind(this)}>
					        <Radio value='boolean'>boolean</Radio>
					        <Radio value='string'>string</Radio>
					      </RadioGroup>
			          </Col>
			          <Col span={3}>
			             <Button type="primary" shape="circle" icon="plus" size='large' onClick={this.onPlusVariableItem.bind(this)}/>
			          </Col>
			        </InputGroup>      
                     {
	                     this.state.variableList.map((item, index) => {
	                     	
					      	return (
					      		item.type=='string'?
					      		<InputGroup key={index} size="large">
						         <Col span={7}>
						            <Input placeholder="键" value={item.key} onChange={this.onChangeVariableKey.bind(this,index)}/>
						          </Col>
								<Col span={10}>
						            <Input
						            style={{width:280}}
						            placeholder="值" value={item.value} onChange={this.onChangeVariableValue.bind(this,index)}/>
						          </Col>
						           <Col span={3}>
						             <Button type="danger" shape="circle" icon="minus" size='large' onClick={this.onMinusVariableItem.bind(this,index)}/>
						          </Col>
						        </InputGroup>
						        :
						        <InputGroup key={index} size="large">
						         <Col span={7}>
						            <Input placeholder="键" value={item.key} onChange={this.onChangeVariableKey.bind(this,index)}/>
						          </Col>
								<Col span={10}>
						            <Select
                        				style={{width:280}}
						            	placeholder="值"
						            	onChange={this.onChangeSelectVariableValue.bind(this,index)}
						            	>
						            	 <Select.Option key={1} value={true}>是</Select.Option>
						            	 <Select.Option key={2} value={false}>否</Select.Option>
						            </Select>
						          </Col>
						           <Col span={3}>
						             <Button type="danger" shape="circle" icon="minus" size='large' onClick={this.onMinusVariableItem.bind(this,index)}/>
						          </Col>
						        </InputGroup>
					      	)
					      })
				      }             
                     </FormItem>
                      <FormItem
                            {...formItemLayout}
                           label="任务评论"
                      >
                          <TextArea rows={4} onChange={this.handleFormComment.bind(this)}/>
                      </FormItem>
                      <FormItem
                            {...formItemLayout}
                           label="任务历史"
                      >
                          <Table
                            rowKey={record => record.id} 
                           	pagination={false}
                           	dataSource={this.state.dataSource} 
                           	columns={columns}
                           />
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

const InstanceApprovalForm = Form.create()(InstanceApproval);
export default InstanceApprovalForm;
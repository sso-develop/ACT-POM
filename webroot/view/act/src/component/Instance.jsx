import React, { Component } from 'react';
import { Table,Form,Input,Button,Row,Col,Modal,Upload,Icon,message,Popconfirm,Divider} from 'antd';
import $ from 'jquery';

const FormItem = Form.Item;
const { TextArea } = Input;
class Instance extends Component {

	constructor(props) {
    	super(props);
    	this.state = {
               dataSource:[],
               visible:false,
               completeTask:{
                id:"",
                variables:"",
                comment:"",
                processInstanceId:""
               },
               pager:{
                    total:0,
                    pageSize:0,
                    loading:false
                }
            }
    	 this.getPersonTask()
    }
    getPersonTask(){
		let that = this;
		$.post("/findPersonTask.json", {},function(data) {
		     if(!data.success){
                message.error(data.msg)
             }else{
                that.setState({dataSource:data.data.result})
             }
		});
	}
	completeTask(){
	    var completeTask = this.state.completeTask;
	    let that = this;
	    var p = completeTask;
	    console.log(p);
	    //return;
        $.post("/completeTask.json", p,function(data) {
            console.log(data);
             if(!data.success){
                message.error(data.msg)
              }else{
                that.closeModal(that)
                that.getPersonTask(that);
              }
		});
	}

	handleFormVariables(currency){
	    var completeTask = this.state.completeTask;
        completeTask.variables = currency.target.value;
	    this.state = {completeTask:completeTask}
	}
	handleFormComment(comment){
	    var completeTask = this.state.completeTask;
        completeTask.comment = comment.target.value;
        this.state = {completeTask:completeTask}
	}
	showModal(record){
	    var completeTask = this.state.completeTask;
	    completeTask.id = record.id;
	   completeTask.processInstanceId = record.processInstanceId
    	this.setState({visible: true,completeTask:completeTask});
    }
    traceprocess(record){
        console.log(record)
         window.open("/traceprocess.json?definitionId="+record.processDefinitionId+"&instanceId="+record.processInstanceId)
    }
	closeModal(){
    	this.setState({ visible: false});
    }

	handleCancel(e){
    	this.closeModal(this);
    }
    handleOk(e){
        this.completeTask(this);
    }
    render() {
        const columns = [{
            title: '任务ID',
            dataIndex: 'id',
            key: 'id',
        }, {
            title: '任务办理人',
            dataIndex: 'assignee',
            key: 'assignee',
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
                            <a onClick = {this.traceprocess.bind(this,record)}>进度图</a>
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
        return(
            <div>
                 <Form>
                    <Row gutter={24}>
                        <Col span={8} style={{ textAlign: 'right' }}>
                             <Button style={{ marginLeft: 8 }} onClick={this.showModal.bind(this)}>新增</Button>
                        </Col>
                    </Row>
                  </Form>
                <Table
                bordered
                loading = {this.state.pager.loading}
                rowKey={record => record.id} dataSource={this.state.dataSource} columns={columns} />
                <Modal
                      title="任务审批"
                       visible={this.state.visible}
                       onCancel={this.handleCancel.bind(this)}
                       onOk={this.handleOk.bind(this)}
                    >
                      <Form
                        horizontal = {'true'} >
                         <FormItem
                               {...formItemLayout}
                              label="参数JSON"
                         >
                            <TextArea rows={4} onChange={this.handleFormVariables.bind(this)}/>
                         </FormItem>
                          <FormItem
                                {...formItemLayout}
                               label="任务评论"
                          >
                              <TextArea rows={4} onChange={this.handleFormComment.bind(this)}/>
                          </FormItem>
                      </Form>
                 </Modal>
            </div>
        )
    }
}

const InstanceForm = Form.create()(Instance);
export default InstanceForm;
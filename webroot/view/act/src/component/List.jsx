import React, { Component } from 'react';
import { Table,Form,Input,Button,Row,Col,Modal,Upload,Icon,message,Popconfirm,Divider} from 'antd';
import $ from 'jquery';

const FormItem = Form.Item;
class List extends Component {

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
    	 this.getDefinition()
    }
    getDefinition(){
		let that = this;
		$.post("/findProcessDefinition.json", {},function(data) {
		     if(!data.success){
                message.error(data.msg)
             }else{
                that.setState({dataSource:data.data.result})
             }
		});
	}
	deleteDeploy(record){
	     var deploymentId = record.deploymentId;
	     console.log(deploymentId)
        let that = this;
	     $.post("/deleteDeploy.json", {deploymentId:deploymentId},function(data) {
            console.log(data);
             if(!data.success){
                message.error(data.msg)
              }else{
                that.getDefinition(that)
              }
          });
	}
	showresource(record){
	    console.log(record.id)
	    window.open("/showresource.json?id="+record.id)

	}

	start(record){
	    console.log(record.key)

	    var variables = {employeeId:"1"};
        $.post("/startProcessInstance.json", {key:record.key,variables:JSON.stringify(variables)},function(data) {
            console.log(data);
        });
	}
	showModal(){
    	this.setState({visible: true});
    }
	closeModal(){
    	this.setState({ visible: false});
    }
	handleCancel(e){
    	this.closeModal(this);
    }
    render() {
        const columns = [{
            title: '名称',
            dataIndex: 'name',
            key: 'name',
        }, {
            title: 'key',
            dataIndex: 'key',
            key: 'key',
        }, {
            title: '定义ID',
            dataIndex: 'id',
            key: 'id',
        },{
              title: '对象ID',
              dataIndex: 'deploymentId',
              key: 'deploymentId',
          },{
              title: '版本',
              dataIndex: 'version',
              key: 'version',
          },{
              title: '操作',
              key: 'action',
              render: (text, record) => {
                var id = record.id;
                return (
                       <span>
                           <Popconfirm title='是否要删除?' onConfirm = {this.deleteDeploy.bind(this,record)} okText="确定" cancelText="取消">
                            <a>删除</a>
                          </Popconfirm>
                          <Divider type="vertical" />
                          <a onClick = {this.showresource.bind(this,record)}>查看</a>
                          <Divider type="vertical" />
                          <a onClick = {this.start.bind(this,record)}>发起流程</a>
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
                      title="流程部署"
                       visible={this.state.visible}
                       onCancel={this.handleCancel.bind(this)}
                    >
                      <Form
                        horizontal = {'true'} >
                         <FormItem
                               {...formItemLayout}
                              label="应用名称"
                         >
                            <Upload
                                {...props}
                            >
                               <Button>
                                 <Icon type="upload" /> 选择部署文件
                               </Button>
                             </Upload>

                         </FormItem>
                      </Form>
                 </Modal>
            </div>
        )
    }
}

const ListForm = Form.create()(List);
export default ListForm;
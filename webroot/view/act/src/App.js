import React, { Component } from 'react';
import {withRouter} from 'react-router-dom' ;
import { Layout } from 'antd';
import Header from "./component/layout/Header.jsx";
import Side from "./component/layout/Side.jsx";

const {Content } = Layout;
class App extends Component {
    constructor(props) {
        super(props)
        let menus = [{
            title: '分组管理',
            icon: 'team',
            to:'/group',
            showSub:true
        },{
            title: '用户管理',
            icon: 'user',
            to:'/user',
            showSub:true
        },{
            title: '流程部署',
            icon: 'usb',
            to:'/definition',
            showSub:true
        },{
              title: '审批流程',
              icon: 'fork',
              to:'/instance',
              showSub:true
          },{
              title: '审批历史',
              icon: 'database',
              to:'/history',
              showSub:true
          }];
        this.state = {
            menus:menus,
            path:this.props.location.pathname
        }
    }
    componentWillReceiveProps(nextProps){
    		this.setState({
        		path:nextProps.location.pathname
        	});
    	}
  render() {
    return (
        <Layout style={{ minHeight: '100vh' }}>
            <Side path = {this.state.path} menus ={this.state.menus}/>
          <Layout>
            <Header path = {this.state.path} menus ={this.state.menus}/>
              <Content style={{ margin: '	16px' }}>
                  <div style={{padding:'5px','overflow':'inherit'}}>
                      {this.props.children}
                  </div>
              </Content>
          </Layout>
        </Layout>
    );
  }
}

export default withRouter(App);

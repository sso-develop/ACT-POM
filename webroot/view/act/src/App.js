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
            title: '流程列表',
            icon: 'user',
            to:'/List',
            showSub:true

        }];
        this.state = {
            menus:menus,
            path:this.props.location.pathname
           // path:"/"
        }
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

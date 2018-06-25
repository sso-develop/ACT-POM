import React, { Component } from 'react';
import { Layout ,Breadcrumb,Icon,Tooltip,Avatar } from 'antd';

const { Header } = Layout;
class Heade extends Component {
    renderBreadcrumb(){
        const routes = [{
            path: '/',
            breadcrumbName: '首页'
        }];

        return (<Breadcrumb key='1' routes={routes} style={{margin: '16px', float:'left'}}/>)
    }
    render() {
        return(
            <Header style={{ background: '#fff', padding: 0 }}>
                {this.renderBreadcrumb()}
            </Header>
        );
    }
}

export default Heade
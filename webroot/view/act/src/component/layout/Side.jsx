import React, { Component } from 'react';
import { Layout } from 'antd';

import Menus from "./Menus.jsx";

const {Sider} = Layout;

class Side extends Component {

    constructor(props) {
        super(props);
        this.state = {
            collapsed: false
        }

    }
    onCollapse (collapsed){
        this.setState({ collapsed });
    }
    render() {
        return (

            <Sider
                collapsible
                collapsed={this.state.collapsed}
                onCollapse={this.onCollapse.bind(this)}
            >
                <div className="logo" />
                <Menus/>
            </Sider>
        );
    }
}
export default Side;
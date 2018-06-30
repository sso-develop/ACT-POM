import React, { Component } from 'react';
import {Link } from 'react-router-dom';
import { Menu, Icon } from 'antd';
const SubMenu = Menu.SubMenu;
class Menus extends Component {
	
	renderMenu(menu) {
		let res = <div></div>;
		let that = this;
		if(menu){
			if (menu.subMenu && menu.subMenu.length !== 0) {
				if(menu.showSub){
					let menuItem = [];
					menu.subMenu.map(function (m, i) {
					 	menuItem.push(that.renderMenu(m));
			       })
					res =  <SubMenu key={menu.to} title={<span><Icon type={menu.icon} /><span>{menu.title}</span></span>}>
						{menuItem}
			          </SubMenu>
				}else{
					res =  <Menu.Item key={menu.to}><Icon type={menu.icon} /><span><Link to={menu.to}>{menu.title}</Link></span></Menu.Item>;
				}
			}else{
				res =  <Menu.Item key={menu.to}><Icon type={menu.icon} /><span><Link to={menu.to}>{menu.title}</Link></span></Menu.Item>;
			}
		}
		return res;
	}
	
  render() {
  	let that = this;
    return (
    	<div>
    		<Menu 
       		 		theme="dark" 
       		 		selectedKeys={[this.props.path]} 
       		 		mode="inline"
       		 	>
       		 		{
		            	this.props.menus.map(function (menu, i) {
	                        return that.renderMenu(menu);
	                    })
		            }
       		 	</Menu>
    	</div>
    );
  }
}

export default Menus;

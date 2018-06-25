import React, { Component } from 'react';
import { Layout } from 'antd';


import Header from "./component/layout/Header.jsx";
import Side from "./component/layout/Side.jsx";

class App extends Component {
  render() {
    return (

        <Layout style={{ minHeight: '100vh' }}>
            <Side/>
          <Layout>
              <Header />
          </Layout>
        </Layout>
    );
  }
}

export default App;

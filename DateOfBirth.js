import React, {Component} from 'react';
import {Platform, StyleSheet,View, StatusBar,Dimensions, DatePickerIOS,
Image,Text,TextInput,TouchableOpacity} from 'react-native';

var wS = Dimensions.get('window');
var dh = wS.height;
var dw = wS.width;
function hh(h){return (dh*h)/670}
function ww(w){return (dw*w)/375}

export default class App extends React.Component {
    constructor(props) {
    super(props);
    this.state = {chosenDate: new Date()};

    this.setDate = this.setDate.bind(this);
  }

  setDate(newDate) {
    this.setState({chosenDate: newDate});
  }
  render() {
    return (
      <View style={styles.container}>
      <StatusBar backgroundColor="#2a56c6" translucent barStyle="light-content" />
          <View style={{height:'100%',justifyContent: 'center',alignItems:'center',width:'100%' }}>
              <View style={{height:'80%', width:'80%'}}>
              <Image source={require('./assets/logo.png')} style={{height:dw*0.2, width:dw*0.2, alignSelf:'center'}} resizeMode="contain"/> 
                <Text style={{fontSize:ww(25), fontWeight:400}}>Date of Birth</Text>

                        <View style={{width:'100%', marginVertical:hh(50)}}>
                                  <DatePickerIOS
                                    date={this.state.chosenDate}
                                    onDateChange={this.setDate}
                                  />
                      </View>
                  <TouchableOpacity style={{width:'100%',paddingVertical:hh(20),marginBottom:hh(20), backgroundColor:'#3380CC',
                                  alignItems:'center', borderRadius:ww(5)}}>
                    <Text style={{fontSize:ww(19),color:'white'}}>Next</Text> 
                    </TouchableOpacity>

              </View>
        </View>

      </View>
    );
  }
}

const STATUSBAR_HEIGHT = Platform.OS === 'ios' ? 20 : StatusBar.currentHeight;

const styles = StyleSheet.create({
    btn:{
    height: hh(50),
    width:(0.30*dw),
    alignItems: 'center',
    justifyContent:'center',
    borderColor: '#3380CC',
    borderRadius: 5,
    borderWidth: 1.5
  },
  container: {
    flex: 1,
  },
  statusBar:{
    height: STATUSBAR_HEIGHT
  }

});

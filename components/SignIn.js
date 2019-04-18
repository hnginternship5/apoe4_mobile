import React, {Component} from 'react';
import {Platform, StyleSheet,View, StatusBar,Dimensions,
Image,Text,TextInput,TouchableOpacity} from 'react-native';

var wS = Dimensions.get('window');
var dh = wS.height;
var dw = wS.width;
function hh(h){return (dh*h)/670}
function ww(w){return (dw*w)/375}

export default class SignIn extends React.Component {
  state={currentPage:'signin'};

  renderLogin(){
    if (this.state.currentPage==='signin'){
      return( 
        <View style={{flex:1, borderWidth:1,width:'100%',marginTop:hh(20), borderRadius:ww(15), padding:10}}>
                 <Text style={{fontSize:ww(25), fontWeight:'400'}}>Sign Up</Text>
                        <View style={{width:'100%', marginVertical:hh(20)}}>
                          <TextInput 
                          placeholder="Email Address"
                          style={[styles.input]} />
                          <TextInput 
                          placeholder="Password"
                          style={styles.input} />
                          
                        <View style={{flexDirection:'row',justifyContent:'space-between',marginBottom:hh(10),}}>
                          <View style={{flexDirection:'row',alignItems:'center'}}>
                          <View style={{height:15,width:15, borderRadius:30, borderWidth:1,borderColor:'blue',
                          backgroundColor:'white',marginRight:10}}></View>
                            <Text>Forgot Password?</Text>
                          </View>

                            <Text style={{color:'#CC3333'}}>Forgot Password</Text>
                        </View>
                      </View>
                <View  style={{alignItems:'center'}}>
                  <TouchableOpacity onPress={()=> this.props.navigation.navigate('AppDrawer')}
                  style={{width:'50%',paddingVertical:hh(20),marginBottom:hh(20), backgroundColor:'#3380CC',
                                  alignItems:'center', borderRadius:ww(5)}}>
                    <Text style={{fontSize:ww(17),color:'white'}}
                    >Sign in</Text>
                  </TouchableOpacity>

                <View style={{width:'100%', flexDirection:'row' ,justifyContent:'space-between'}}>
                                  <TouchableOpacity style={styles.btn}>
                      <Image source={require('../assets/fb.png')} style={{height:ww(30), width:ww(30)}} />
                  </TouchableOpacity>

                  <TouchableOpacity style={styles.btn}>
                     <Image source={require('../assets/google.png')} style={{height:ww(30), width:ww(30)}} />
                  </TouchableOpacity>
                </View>

                </View>

        </View>
      );
    }
    else if(this.state.currentPage==='signup'){
    return(
     <View style={{flex:1,width:'100%',marginTop:hh(20), borderRadius:ww(15), padding:10,}}>
       <Text style={{fontSize:ww(25), fontWeight:'400'}}>Create New</Text>
           <View style={{width:'100%', marginVertical:hh(20)}}>
                <TextInput 
                placeholder="Full Name"
                style={styles.input} /> 
                <TextInput 
                placeholder="Email Address"
                style={styles.input} />
                <TextInput 
                placeholder="Password"
                style={[styles.input]} />
                <TextInput 
                placeholder="Confirm Password"
                style={styles.input} />
          <View style={{flexDirection:'row',alignItems:'center', width:'100%'}}>
                <View style={{height:ww(20),width:ww(20), borderWidth:1,borderColor:'blue',
                backgroundColor:'white',marginRight:10}}></View>
                  <Text>Accept Terms and Conditions?</Text>
                </View>
            </View>

        <TouchableOpacity onPress={()=>this.props.navigation.navigate('DOB')}
        style={{paddingVertical:hh(20),backgroundColor:'#3380CC',width:'40%',alignSelf:'center',
                                alignItems:'center', borderRadius:ww(5)}}>
          <Text style={{fontSize:ww(18),color:'white'}}
          >Sign Up</Text>
        </TouchableOpacity>
      </View>      
    );  
    }
  }
  render() {
    return (
      <View style={styles.container}>
      <StatusBar backgroundColor="#2a56c6" translucent barStyle="light-content" />
          <View style={{height:'100%',justifyContent: 'center',alignItems:'center',width:'100%' }}>
              <View style={{height:'80%', width:'80%'}}>
              <Image source={require('../assets/logo.png')} style={{height:dw*0.2, width:dw*0.2, alignSelf:'center'}} resizeMode="contain"/> 
              <View style={{flexDirection:'row', width:'100%', borderRadius:ww(20)}}>
                      <TouchableOpacity 
                      onPress={()=>this.setState({currentPage:'signin'})}
                      style={{backgroundColor:this.state.currentPage==='signin'?'#3380CC':'#99bfe5', 
                      width:'50%',alignItems:'center',justifyContent:'center',
                           borderBottomLeftRadius:ww(15),borderTopLeftRadius: ww(15)}}>
                          <Text style={{color:'white',fontSize:ww(25)}}>Sign In</Text>
                      </TouchableOpacity>

                       <TouchableOpacity 
                       onPress={()=>this.setState({currentPage:'signup'})}
                       style={{backgroundColor:this.state.currentPage==='signin'?'#99bfe5':'#3380cc',
                       width:'50%',height:hh(50),alignItems:'center',justifyContent:'center',
                          borderBottomRightRadius:ww(15),borderTopRightRadius: ww(15)}} >
                          <Text style={{color:'white',fontSize:ww(25), fontWeight:'400'}}>Create New</Text>
                      </TouchableOpacity>
                </View>
                {this.renderLogin()}
              </View>
        </View>

      </View>
    );
  }
}

const styles = StyleSheet.create({
    btn:{
    height: hh(50),
    width:(0.30*dw),
    alignItems: 'center',
    justifyContent:'center',
    borderRadius: 5,
  },
  input:{
    backgroundColor:'white',
    padding:ww(20),
    height:hh(50),
    width:'100%',
    borderRadius:5,
    borderWidth:2,
    borderColor:'#3380CC',
    marginBottom:hh(20)
  },
  container: {
    flex: 1,
  }
});

import React from "react";
import { Button, View, AsyncStorage, Image, StyleSheet, ScrollView, TouchableOpacity, Text, Dimensions, TextInput } from "react-native";

import Logo from '../../assets/logo.png';

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
  },
  logo: {
    marginTop: 50,
  },
  authContainer: {
    flexDirection: 'row'
  },
  loginScreenButton: {
    alignItems: 'center',
    backgroundColor: '#3380CC',
    padding: 10,
    borderBottomLeftRadius: 5,
    borderTopLeftRadius: 5,
    width: 150
  },
  signUpScreenButton: {
    alignItems: 'center',
    backgroundColor: 'rgba(51, 128, 204, 0.5)',
    padding: 10,
    borderBottomRightRadius: 5,
    borderTopRightRadius: 5,
    width: 150
  }
})

export default class SignInScreen extends React.Component {
  static navigationOptions = {
    title: "Please sign in"
  };

  state = {
    view: 'signIn'
  }

  render() {
    const { view } = this.state;
    return (
      <View style={styles.container}>
        <ScrollView>
          <View style={styles.logo}>
            { /* <Button title="Sign in!" onPress={this._signInAsync} /> */ }
            <Image source={ Logo } />
          </View>
          <View style={styles.authContainer}>
            <TouchableOpacity
              style={styles.loginScreenButton}
              onPress={() => this.setState({ view: 'signIn' })}
              underlayColor='#fff'>
              <Text style={styles.loginText}>Sign In</Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.signUpScreenButton}
              onPress={() => this.setState({ view: 'createNew' })}
              underlayColor='#fff'>
              <Text style={styles.loginText}>Create New</Text>
            </TouchableOpacity>
          </View>
          {view === 'signIn' ? <View style={{flex:1, borderWidth:1,width:'100%',marginTop: 20, borderRadius: 5, padding: 10}}>
            <Text style={{fontSize: 25, fontWeight:'400'}}>Sign Up</Text>
            <View style={{width:'100%', marginVertical:20}}>
              <TextInput 
              placeholder="Email Address"
              style={[styles.input]} />
              <TextInput 
              placeholder="Password"
              style={styles.input} />
                   
              <View style={{flexDirection:'row',justifyContent:'space-between',marginBottom: 10,}}>
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
                style={{width:'50%',paddingVertical:20,marginBottom:20, backgroundColor:'#3380CC',
                alignItems:'center', borderRadius:5}}>
                  <Text style={{fontSize:17,color:'white'}}>Sign in</Text>
                </TouchableOpacity>

              <View style={{width:'100%', flexDirection:'row' ,justifyContent:'space-between'}}>
                <TouchableOpacity style={styles.btn}>
                  <Image source={require('../../assets/fb.png')} style={{height: 30, width: 30}} />
                </TouchableOpacity>

                <TouchableOpacity style={styles.btn}>
                    <Image source={require('../../assets/google.png')} style={{height: 30, width: 30}} />
                </TouchableOpacity>
              </View>
            </View>
          </View> : <Text>coming soon</Text>}
        </ScrollView>
      </View>
    );
  }

  _signInAsync = async () => {
    await AsyncStorage.setItem("userToken", "abc");
    this.props.navigation.navigate("Main");
  };
}

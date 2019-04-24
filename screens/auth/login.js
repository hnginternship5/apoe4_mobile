import React from "react";
import { View, AsyncStorage, Image, StyleSheet, ScrollView, TouchableOpacity, Text, TextInput, Dimensions } from "react-native";
import Icon from "react-native-vector-icons/FontAwesome";
import { Input, Image } from "react-native-elements";

import Logo from '../../assets/logo.png';


const { width } = Dimensions.get('window')

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    padding: 10,
  },
  logo: {
    marginTop: 50,
    marginBottom: 50,
    alignSelf: 'center'
  },
  authContainer: {
    flexDirection: 'row',
    width: width - 20,
    // alignItems: 'center',
    // justifyContent: 'center'
  },
  authText: {
    fontSize: 20,
    fontWeight: '700',
    color: '#FFF'
  },
  loginScreenButton: {
    alignItems: 'center',
    // backgroundColor: '#3380CC',
    backgroundColor: 'rgba(51, 128, 204, 0.5)',
    padding: 15,
    borderBottomLeftRadius: 16,
    borderTopLeftRadius: 16,
    width: '50%'
  },
  signUpScreenButton: {
    alignItems: 'center',
    backgroundColor: 'rgba(51, 128, 204, 0.5)',
    padding: 15,
    borderBottomRightRadius: 16,
    borderTopRightRadius: 16,
    width: '50%'
  },
  signInContainer: {
    flex: 1,
    borderWidth:1,
    borderRadius: 15, 
    borderColor: '#FFF',
    marginVertical: 20,
    padding: 10,
    backgroundColor: '#FFF',
    shadowColor: 'rgba(0, 0, 0, 0.15)',
    shadowRadius: 15,
    shadowOpacity: 1
  },
  input:{
    backgroundColor:'white',
    padding:20,
    height:50,
    width:'100%',
    borderRadius:5,
    borderWidth:2,
    borderColor:'#3380CC',
    marginBottom:20
  },
})

export default class SignInScreen extends React.Component {
  static navigationOptions = {
    header: null
  };

  state = {
    view: 'signIn',
  }

  render() {
    const { view } = this.state;
    return (
      <View style={styles.container}>
        <ScrollView>
          <View>
            { /* <Button title="Sign in!" onPress={this._signInAsync} /> */ }
            <Image style={styles.logo} source={ Logo } />
          </View>
          <View style={styles.authContainer}>
            <TouchableOpacity
              style={[styles.loginScreenButton, { backgroundColor: `${view === 'signIn' ? '#3380CC' : 'rgba(51, 128, 204, 0.5)' }` }]}
              onPress={() => this.setState({ view: 'signIn' })}
              underlayColor='#fff'>
              <Text style={styles.authText}>Sign In</Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={[styles.signUpScreenButton, { backgroundColor: `${view === 'createNew' ? '#3380CC' : 'rgba(51, 128, 204, 0.5)' }` }]}
              onPress={() => this.setState({ view: 'createNew' })}
              underlayColor='#fff'>
              <Text style={styles.authText}>Create New</Text>
            </TouchableOpacity>
          </View>
          <View style={styles.signInContainer}>
          {view === 'signIn' ? <View>
          <Text style={{fontSize: 25, fontWeight:'700'}}>Sign In</Text>
          <View style={{width:'100%', marginVertical:20}}>
              <TextInput 
              placeholder="Email Address"
              style={[styles.input]} />
              <TextInput 
              placeholder="Password"
              style={styles.input} />
                   
              <View style={{flexDirection:'row',justifyContent:'space-between',marginBottom: 10,}}>
                <View style={{flexDirection:'row',alignItems:'center'}}>
                <View style={{height:15,width:15, borderWidth:1.5,borderColor:'#3380CC',
                backgroundColor:'white',marginRight:10}}></View>
                <Text>Remember Me</Text>
              </View>

              <Text style={{color:'#CC3333'}}>Forgot Password?</Text>
                 </View>
               </View>

               <View style={{alignItems:'center'}}>
                <TouchableOpacity onPress={()=> this._signInAsync}
                style={{width:'50%',paddingVertical:20,marginBottom:20, backgroundColor:'#3380CC',
                alignItems:'center', borderRadius:5}}>
                  <Text style={{fontSize:17, fontWeight: '700', borderRadius: 10, color:'#FFF'}}>Sign in</Text>
                </TouchableOpacity>
            </View>
            <Text style={{alignSelf:'center'}}>or</Text>
            <View style={{flexDirection:'row', justifyContent:'space-around'}}>
              <TouchableOpacity style={styles.btn}>
                <Image source={require('../../assets/google.png')} style={{height: 30, width: 30}} />
              </TouchableOpacity>
              <TouchableOpacity style={styles.btn}>
                <Image source={require('../../assets/fb.png')} style={{height: 30, width: 30}} />
              </TouchableOpacity>
              </View>
            
        </View> : <View style={{flex:1,width:'100%',marginTop:20, borderRadius:15, padding:10,}}>
        <Text style={{fontSize:25, fontWeight:'400'}}>Create New</Text>
            <View style={{width:'100%', marginVertical:20}}>
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
                 <View style={{height:20,width:20, borderWidth:1,borderColor:'blue',
                 backgroundColor:'white',marginRight:10}}></View>
                   <Text>Accept Terms and Conditions?</Text>
                 </View>
             </View>
 
         <TouchableOpacity onPress={()=>this.props.navigation.navigate('DOB')}
         style={{paddingVertical:20,backgroundColor:'#3380CC',width:'40%',alignSelf:'center',
                                 alignItems:'center', borderRadius:5}}>
           <Text style={{fontSize:18,color:'white'}}
           >Sign Up</Text>
         </TouchableOpacity>
       </View> }
          </View>
        </ScrollView>
      </View>
    );
  }

  _signInAsync = async () => {
    await AsyncStorage.setItem("userToken", "abc");
    this.props.navigation.navigate("Main");
  };
}

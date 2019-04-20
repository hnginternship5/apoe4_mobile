import React, {Component} from 'react';
import {Platform, StyleSheet,View, StatusBar,Dimensions,Image,Text,ScrollView,
        TouchableOpacity,} from 'react-native';
import ScorePie from './pies/ScorePie'
import GymPie from './pies/GymPie'
import SleepPie from './pies/SleepPie'
import { FontAwesome } from '@expo/vector-icons';
var wS = Dimensions.get('window');
var dh = wS.height;
var dw = wS.width;
function hh(h){return (dh*h)/670}
function ww(w){return (dw*w)/375}

export default class HomePage extends React.Component {
  state={feeling:'great'};

  render() {
    return (
      <View style={styles.container}>
          <View style={{flexDirection:'row',padding:ww(20),justifyContent:'space-between',}}>
              <View style={{flexDirection:'row',alignSelf:'flex-start'}}>
               <TouchableOpacity 
                  onPress={()=>this.props.navigation.openDrawer()}
                  activeOpacity={0.7}>
                <Image source={require('../assets/profile.png')} style={{height:ww(40), width:ww(40), marginRight:ww(10)}} />
               </TouchableOpacity>
                <View>
                  <Text style={{fontSize:ww(17), fontWeight:'400', color:'#3380CC'}}>Hello,</Text>
                  <Text style={{fontSize:ww(17), fontWeight:'400', color:'#3380CC'}}>John Doe</Text>
                </View>
              </View>

              <View style={{alignSelf:'flex-end'}}>
                <FontAwesome name='bell' size={ww(30)} />
                <View style={{height:ww(20),width:ww(20),borderRadius:ww(40), backgroundColor:'#EB5757',
                justifyContent:'center',alignItems:'center' ,position:'absolute', top:0,right:0}}>
                  <Text style={{fontSize:ww(13),color:'white'}}>3</Text>
                </View>
              </View>
        </View>

          <View style={{height:hh(190), backgroundColor:'#3380CC', borderWidth:1,flexDirection:'row',paddingHorizontal:ww(15)
                      ,justifyContent:'space-between',alignItems:'center'}}>
          <View style={{alignContent:"center"}}>
            <GymPie />
              <Text style={{color:'white',fontSize:18,fontWeight:'bold'}}>2Hrs</Text>
              <Text style={{color:'white',fontSize:15}}>Exercises</Text>
          </View>

           <View style={{alignContent:"center"}}>
            <ScorePie />
              <Text style={{color:'white',fontSize:20,fontWeight:'bold'}}>APO</Text>
              <Text style={{color:'white',fontSize:15}}>Score</Text>
          </View> 
            
           <View style={{alignContent:"center"}}>
            <SleepPie />
              <Text style={{color:'white',fontSize:18,fontWeight:'bold'}}>6Hrs</Text>
              <Text style={{color:'white',fontSize:15}}>Sleep</Text>
          </View>   

          </View>

        <ScrollView style={{flex:1, alignItems:'center'}}>
          <View style={{marginTop:hh(40), flexDirection:'row'}}>
            <Image source={require('../assets/fb.png')} style={{width:ww(20),height:ww(20), borderRadius:ww(10)}} />
            <Text style={{fontSize:ww(18),fontWeight:'bold',color:'#676767'}}>How are you Today?</Text>
          </View>
      
              <TouchableOpacity 
                onPress={()=>this.setState({feeling:'great'})}
                style={{backgroundColor:this.state.feeling==='great'?'#3380CC':'#fff',
                        borderColor:'#3380CC',
                        borderWidth:1,
                        height:ww(56),width:ww(140),alignItems:'center',justifyContent:'center',marginTop:hh(15),
                        borderRadius:ww(7)}}>
                  <Text style={{color:this.state.feeling==='great'?'white':'#676767',
                  fontSize:ww(20)}}>Great</Text>
             </TouchableOpacity>

            <TouchableOpacity 
                onPress={()=>this.setState({feeling:'good'})}
                 style={{backgroundColor:this.state.feeling==='good'?'#3380CC':'#fff',
                        borderColor:'#3380CC',
                        borderWidth:1,
                        height:ww(56),width:ww(140),alignItems:'center',justifyContent:'center',marginTop:hh(15),
                        borderRadius:ww(7)}}>
                    <Text style={{color:this.state.feeling==='good'?'white':'#676767',fontSize:ww(20)}}>Good</Text>
             </TouchableOpacity>

             <TouchableOpacity 
                onPress={()=>this.setState({feeling:'fine'})}
                 style={{backgroundColor:this.state.feeling==='fine'?'#3380CC':'#fff',
                        borderColor:'#3380CC',
                        borderWidth:1,
                        height:ww(56),width:ww(140),alignItems:'center',justifyContent:'center',marginTop:hh(15),
                        borderRadius:ww(7)}}>
                    <Text style={{color:this.state.feeling==='fine'?'white':'#676767',fontSize:ww(20)}}>Fine</Text>
             </TouchableOpacity>
             


        </ScrollView>
      </View>
    );
  }
}

const STATUSBAR_HEIGHT = Platform.OS === 'ios' ? 20 : StatusBar.currentHeight;

const styles = StyleSheet.create({
  btn:{
    height: hh(50),
    width:(0.35*dw),
    alignItems: 'center',
    justifyContent:'center',
    borderColor: '#ed3851',
    borderRadius: 5,
    borderWidth: 1.5
  },
  topText:{
    fontSize: ww(17),
    color:'#474747'
  },
  container: {
    flex: 1,
    backgroundColor:'white',
    paddingTop:hh(20)
  },

});

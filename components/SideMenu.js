import React, {Component} from 'react';
import {NavigationActions} from 'react-navigation';
import {ScrollView, Text, View, StyleSheet,Dimensions, TouchableOpacity, Image} from 'react-native';
import { FontAwesome } from '@expo/vector-icons';

var wS = Dimensions.get('window');
var dh = wS.height;
var dw = wS.width;
function hh(h){return (dh*h)/670}
function ww(w){return (dw*w)/375}

export default class SideMenu extends Component {
  navigateToScreen = (route) => () => {
    const navigateAction = NavigationActions.navigate({
      routeName: route
    });
    this.props.navigation.dispatch(navigateAction);
  }



  render ()
   {
    return (
      <View style={{backgroundColor: '#f0f0f0', flex: 1}}>
          <View style={styles.sideHeader}>
              <Image source={require('../assets/profile.png')} style={styles.profileImg}/>
              <Text style={{fontSize:ww(20), color:'white',fontWeight:'bold'}}>John Doe</Text>
              <Text style={{fontSize:ww(16), color:'white'}}>male, 32 years</Text>
          </View>

          <View style={{flexDirection:'row', backgroundColor:'#5897D5',height:hh(80),justifyContent:'center',alignItems:'center'}}>
              <View>
                <Text style={{fontSize:ww(16), color:'white'}}>Height</Text>
                <Text style={{fontSize:ww(16), color:'white'}}>185 cm</Text>
              </View>

              <View>
                <Text style={{fontSize:ww(16), color:'white'}}>Weighht</Text>
                <Text style={{fontSize:ww(16), color:'white'}}>176 kg</Text>
              </View>
          </View>

      <View style={{marginTop:hh(50)}}>
        <TouchableOpacity style={styles.menuBtn}>
              <FontAwesome name="sliders-h" size={20}
              style= {[styles.drawrIcon && this.props.activeItemKey === 'Inbox' ? styles.currentIcon : styles.drawrIcon ]} />
              <Text style={[styles.menuTxt && this.props.activeItemKey === 'Inbox' ? styles.currentText : styles.menuTxt ]}>
              Settings</Text>

          </TouchableOpacity>
                <TouchableOpacity style={styles.menuBtn} onPress={this.navigateToScreen('SignIn')}>
              <FontAwesome name="sign-out-alt" size={20}
              style= {[styles.drawrIcon && this.props.activeItemKey === 'Inbox' ? styles.currentIcon : styles.drawrIcon ]} />
              <Text style={[styles.menuTxt && this.props.activeItemKey === 'Inbox' ? styles.currentText : styles.menuTxt ]}>
              Logout</Text>
          </TouchableOpacity>
      </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
    sideHeader:{
      height: hh(200),
      alignItems:'center',
      justifyContent: 'center',
      backgroundColor:'#3380CC'
    },
    profileImg:{
      height:ww (150),
      width: ww(150),
      borderRadius:ww(75)
    },

   menuBtn:{
     paddingVertical: 12,
     borderColor: '#ddd',
    flexDirection:'row',
    justifyContent: 'flex-start'
      },
  drawrIcon:{
  marginHorizontal: 20
    },

  menuTxt:{
  fontSize: 18
    },

  currentText:{
    color: '#D81A60',
    fontSize: 19
  },

  currentIcon:{
    color: '#D81A60',
    marginHorizontal: 20
  },
});

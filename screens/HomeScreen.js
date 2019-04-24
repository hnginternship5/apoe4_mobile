import React from "react";
import {
  Image,
  Platform,
  ScrollView,
  StyleSheet,
  Text,
  TouchableOpacity,
  View
} from "react-native";

export default class HomeScreen extends React.Component {
  static navigationOptions = {
    title: "Home"
  };

  render() {
    return (
      <View style={styles.container}>
        <ScrollView
          style={styles.container}
          contentContainerStyle={styles.contentContainer}
        >
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

            <TouchableOpacity style={{alignSelf:'flex-end'}} onPress={()=> this.props.navigation.navigate('Notifications')}>
              <FontAwesome name='bell' size={ww(30)} />
              <View style={{height:ww(20),width:ww(20),borderRadius:ww(40), backgroundColor:'#EB5757',
              justifyContent:'center',alignItems:'center' ,position:'absolute', top:0,right:0}}>
                <Text style={{fontSize:ww(13),color:'white'}}>3</Text>
              </View>
            </TouchableOpacity>
      </View>
       <View style={{flex:1}}>
        {this.renderTabs()}
       </View>
       <View style={{width:'100%',justifyContent:'space-between', paddingHorizontal:ww(15), alignItems:'center', height:hh(80), borderTopColor:'#ddd', flexDirection:'row',
       borderTopWidth:0.5, backgroundColor:'white'}}>
        <TouchableOpacity onPress={()=>this.setState({currentTab:'ResultsTab'})} style={{alignItems:'center'}}>
          <Image source={this.state.currentTab==='ResultsTab'?require('../assets/resultsActive.png'):require('../assets/results.png')}
          resizeMode="contain" style={styles.tabBtn} />
          <Text style={{fontSize:ww(15), color:this.state.currentTab==='ResultsTab'?'#3380CC':'#C4C4C4'}}>Results</Text>
        </TouchableOpacity>

         <TouchableOpacity onPress={()=>this.setState({currentTab:'TodayTab'})} style={{alignItems:'center'}}>
          <Image source={this.state.currentTab==='TodayTab'?require('../assets/todayActive.png'):require('../assets/today.png')}
          resizeMode="contain" style={styles.tabBtn} />
          <Text style={{fontSize:ww(15), color:this.state.currentTab==='TodayTab'?'#3380CC':'#C4C4C4'}}>Today</Text>
        </TouchableOpacity>

         <TouchableOpacity onPress={()=>this.setState({currentTab:'ForumsTab'})} style={{alignItems:'center'}}>
          <Image source={this.state.currentTab==='ForumsTab'?require('../assets/forumsActive.png'):require('../assets/forums.png')}
          resizeMode="contain" style={styles.tabBtn} />
          <Text style={{fontSize:ww(15), color:this.state.currentTab==='ForumsTab'?'#3380CC':'#C4C4C4'}}>Forums</Text>
        </TouchableOpacity>
        </ScrollView>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff"
  },
  developmentModeText: {
    marginBottom: 20,
    color: "rgba(0,0,0,0.4)",
    fontSize: 14,
    lineHeight: 19,
    textAlign: "center"
  },
  contentContainer: {
    paddingTop: 30
  },
  welcomeContainer: {
    alignItems: "center",
    marginTop: 10,
    marginBottom: 20
  },
  welcomeImage: {
    width: 100,
    height: 80,
    resizeMode: "contain",
    marginTop: 3,
    marginLeft: -10
  },
  getStartedContainer: {
    alignItems: "center",
    marginHorizontal: 50
  },
  homeScreenFilename: {
    marginVertical: 7
  },
  codeHighlightText: {
    color: "rgba(96,100,109, 0.8)"
  },
  codeHighlightContainer: {
    backgroundColor: "rgba(0,0,0,0.05)",
    borderRadius: 3,
    paddingHorizontal: 4
  },
  getStartedText: {
    fontSize: 17,
    color: "rgba(96,100,109, 1)",
    lineHeight: 24,
    textAlign: "center"
  },
  tabBarInfoContainer: {
    position: "absolute",
    bottom: 0,
    left: 0,
    right: 0,
    ...Platform.select({
      ios: {
        shadowColor: "black",
        shadowOffset: { height: -3 },
        shadowOpacity: 0.1,
        shadowRadius: 3
      },
      android: {
        elevation: 20
      }
    }),
    alignItems: "center",
    backgroundColor: "#fbfbfb",
    paddingVertical: 20
  },
  tabBarInfoText: {
    fontSize: 17,
    color: "rgba(96,100,109, 1)",
    textAlign: "center"
  },
  navigationFilename: {
    marginTop: 5
  },
  helpContainer: {
    marginTop: 15,
    alignItems: "center"
  },
  helpLink: {
    paddingVertical: 15
  },
  helpLinkText: {
    fontSize: 14,
    color: "#2e78b7"
  }
});

# slideSelectionList
The goal is to develop an amazing library with the latest android library to make a nagivation based menu. The viewpager based animated pushed level by level list menu support both multiple and single selection. The library to resolve real time sliding selection list.

## Features:
- [x] level based menu with recyclerview and viewpager
- [ ] custom transitions
- [ ] mulitple selection and single selection combinations

## Feature Sample App:
- [Applied HB Filte EZ](https://github.com/jjhesk/slideSelectionList/blob/master/SmartSelectionList/app/src/main/java/com/tradlulu/demoCollectionList/AppliedHBFilteEZ.java) for applied API application
- [Basic App Logic](https://github.com/jjhesk/slideSelectionList/blob/master/SmartSelectionList/app/src/main/java/com/tradlulu/demoCollectionList/AppliedHBFilter.java) for non-API simple implemenation

Applied with logic from HbSelectionFragment which is the enhanced flow logic from backward and forward selection list.


## implemenation
There is nothing to supprt Ecilipse. Sorry. No Ecilipse! Instead you can take a look at the gradle implementation
```gradle
repositories {
   maven { url 'http://dl.bintray.com/jjhesk/maven/' }
}


compile 'com.hkm.slideSelectionList:slideselection:1.0.0'
```

# Walkthrough
A simple library to build a simple walkthrough activity.


## Target platforms
API level 19 or later

## Latest version
Version 1.0.0 (July,2019)

## Screenshots

<img src="https://github.com/gehadfatah/Walkthrough/raw/master/screenshots/screen_1.png" alt="alt text" width="400">
<img src="https://github.com/gehadfatah/Walkthrough/raw/master/screenshots/screen_2.png" alt="alt text" width="400">
<img src="https://github.com/gehadfatah/Walkthrough/raw/master/screenshots/screen_3.png" alt="alt text" width="400">

## Getting started
This library is published on jCenter. Just add these lines to `build.gradle`.

```groovy
dependencies {
implementation 'com.github.gehadfatah:walkthrough:3.2.0'

}

```
Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}

```


## Usage
1- Just extend **WalkthroughActivity**
```java
public class MainActivity extends WalkthroughActivity{

}
```
2- Create new object from **WalkthroughItem** which represent a single page and add it to the activity.
```java
WalkthroughItem page = new WalkthroughItem(drawableId, "", "");
addPage(page);
```

3- You can use this function to customize you activity :

a- The type of progress, dots or horizontal bar by default it's dots :
**DOTS_TYPE** or **BAR_TYPE** 
	
    setProgressType(progressType);
and if you want to hide the progress just call 
```java
hideProgress();
```
b- The color of progress :
```java
setProgressBarColor(R.color.colorName);
```
c- Transition : you can use any of this built in transitions 
**ACCORDION_TRANSFORMER**,
**BACK_TO_FORE_TRANSFORMER**,
**FORE_TO_BACK_TRANSFORMER**,
**DEPTH_TRANSFORMER**,
**SCALE_IN_OUT_TRANSFORMER**,
**STACK_TRANSFORMER**,
**ZOOM_OUT_SLIDE_TRANSFORMER**,
**ZOOM_OUT_TRANSFORMER**,
```java
setTransitionType(transition);
```
and if you want the default transition for the viewPager don't call this function.

d- To decide what happen when this Walkthrough finish just override onFinish() function.
```java
@Override
public void onFinishButtonPressed() {
	enter code here
	       //finish();
           // startActivity(new Intent(this, LandActivity.class));
}
```
    

# PinedPageSample
演示悬停tab栏的效果

### 实现思路
将3个列表放在3个``Fragment``中，利用``ViewPager``实现左右滑动。Tab的实现我偷懒找了个现成的，主要问题是3个列表有一个共同的Header。我这里把这个Header放在Activity的布局中，然后给3个列表分别设置一个透明的header，监听列表滚动，使得``Acitivity``中的header和Tab跟着``ListView``一起滚动。还有一个问题是，当Tab没有滚动到最顶部的时候，3个列表的滚动距离需要一致，这里在``ViewPager``切换监听的时候去设置了一下相邻Fragment中的``ListView``的位置。下拉刷新偷懒用了supportv4中的控件来实现（貌似流利说也是这样做的:p ）

### 缺陷
这个实现在1，3 ``Fragment``之间切换的时候列表位置同步有问题

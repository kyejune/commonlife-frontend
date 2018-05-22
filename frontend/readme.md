# FrontEnd Dev Guide

## Required

> npm or yarn, cordova,

## Set `config.js`

Create `src/config/config.js` file.

```
cp src/config/config.config-build.js src/config/config.js
```

## Web Test

```js
yarn # or npm i
yarn start # or npm start
```

## Mobile Test Common
- https://cordova.apache.org/docs/ko/latest/guide/cli/index.html
- https://cordova.apache.org/docs/ko/latest/guide/platforms/android/
- https://cordova.apache.org/docs/ko/latest/guide/platforms/ios/
```

```


## Mobile Device Test
```
cordova add platform ...
```

## Mobile Emulate Test
```
cordova emulate ...

```

## App Packaging
```
- yarn build && cordova build 
- android or Xcode 에서 app packaging
- app icon 과 splash screen은 /frontend/res/xcode, android studio에...
```


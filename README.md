[![](https://jitpack.io/v/jblejder/rxprefs.svg)](https://jitpack.io/#jblejder/rxprefs)


# RxPrefs
Reactive Extensions for Android SharedPreferences.

## How to use

There are two ways to use this library.
1. Use as a decoration and then inject RxPrefs to other components

        val rxPrefs = RxPrefs(sharedPreference)
        
2. Or use as an extension

        val rxPrefs = sharedPreference.rx

        
Notice that extension is only an alias for first approach but you will get same result because library uses on `SharedPreferences.OnSharedPreferenceChangeListener`.

### API

RxPrefs mimics SharedPreferences interface therefore you will find similiar methods as in normal shared preferences.
All methods return `Observable<TYPE>` where TYPE is one of allowed types.

    rxPrefs.string(key: String, default: String)
    rxPrefs.int(key: String, default: Int)
    rxPrefs.stringSet(key: String, default: Set<String>)
    rxPrefs.long(key: String, default: Long)
    rxPrefs.float(key: String, default: Float)
    rxPrefs.boolean(key: String, default: Boolean)
    rxPrefs.all()
        
As in original shared preferences `string` and `stringSet` can return null as default value.
    
    rxPrefs.string(key: String)
    rxPrefs.stringSet(key: String)
    
RxJava does not allow nulls so this Observable will return event `Changed` that stores value inside.

## Custom

If you want to read custom values from preferences you can use your own listener:

    rxPrefs.custom(publisher: OnPrefsChangedPublisher<T>)
    
## Gradle

1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

        allprojects {
                repositories {
                        ...
                        maven { url 'https://jitpack.io' }
                }
        }
        
2. Add the dependency
        
        dependencies {
	        implementation 'com.github.jblejder:rxprefs:0.1'
        }

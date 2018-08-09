# JoomTest

Test sample with Giphy API gifs grid and viewer. App made for Joom recruitment process. Done in approx. 11 hours.

### How to lauch
Add giphy api key in your local `gradle.properties` like `giphy_api_key=<your_apikey_here>` and build as usual

### Libraries used
* Retrolambda — A little bit overkill fopr small apps, but for fast development it was good.
* RxJava2 — added for consestent flow between logic and UI and vise versa.
* RxAndroid — normaly I don't use it, just added here because of `AndroidSchedulers`, usually it's the same but implemented inside an application.
* Fresco — highweight, yes, but it's really easy to show webp gifs using it, so I mnade this choise.
* Support Tabs — for giphy user profile showing inside webview.
* Retrofit — for fast rest api client impllementation.
* Gson — a little bit overkill for such a small app, but for speeding up developemnt it's ok.
* Google Lifecycle Extensions — I think for this app google's `ViewModel` implementation was enough.

### Good to notice
* I'm not managing lifecycle inside ViewModel and not saving state intentionally, as I think leaving it simplier here will be better. For example, I can use Subjects for it to save state inside ViewModel between Activity recreations.

* Also, by making state saving in ViewModels we can bind and unbind to VMs in onStart/onStop, but it will require work as we also need to hold and refresh `paging` and `refresh` and `retry` signals in case our UI will be destroyed. So for now its just onCreate/onDestroy with mno state saving.

* I made `PreviewActivity` singleTop, so it can be launched inside other apps like messengers when clicking deeplink.

* I left just one `ViewModel` class because here it is kind of "pure" and holding no logic data, just `RestAdapter`, which seems ok for me.

* I've done nothing with proguard and signing and all the other infrastructure stuff


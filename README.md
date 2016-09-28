Resource Templating Functions
=======================

A [module](https://documentation.magnolia-cms.com/display/DOCS/Modules) for the [Magnolia CMS](http://www.magnolia-cms.com) for generating links to resources easily in your Magnolia CMS based templates.

Module will register new templating function class that one can then register with the renderer e.g. under ```/modules/rendering/renderers/freemarker/contextAttributes``` as

```
hcmcfn
  componentClass=com.neatresults.mgnltweaks.resources.ResourcesTemplatingFunctions
  name=hcmcfn
```

The above registration is done automatically for freemarker renderer and is meant only as example for other type of renderers one might be using.

Once registered, it is possible to use functions in templates as follows:

```${hcmcfn.css("/travel-demo-theme.*css")}  ```

will print 
```
<link rel="stylesheet" type="text/css"  href="/magnoliaAuthor/travel/.resources/travel-demo-theme/css/travels-magnolia.css"/>
<link rel="stylesheet" type="text/css"  href="/magnoliaAuthor/travel/.resources/travel-demo-theme/libs/slick-carousel/css/slick-theme.css"/>
...
```

similarly, functions

```${hcmcfn.css(["/travel-demo-theme.*css", ".*magnolia.*css"])}  ```

will print links for both of the provided patterns

For more advanced use, there is variant of the above functions that allows one to specify additional attributes:

```${hcmcfn.css("/travel-demo-theme.*css", "media='all'")}  ``` respectively ```${hcmcfn.css(["/travel-demo-theme.*css", ".*magnolia.*css"], "media='all'")}  ```

and it will print same output as before, but with extra attributes included:

```
<link rel="stylesheet" type="text/css" media='all' href="/magnoliaAuthor/travel/.resources/travel-demo-theme/css/travels-magnolia.css"/>
<link rel="stylesheet" type="text/css" media='all' href="/magnoliaAuthor/travel/.resources/travel-demo-theme/libs/slick-carousel/css/slick-theme.css"/>
...
```

All of the above, also exists in variant providing cacheable (fingerprinted) output:

```${hcmcfn.cachedCss("/travel-demo-theme.*css")} ```, ```${hcmcfn.cachedCss(["/travel-demo-theme.*css", ".*magnolia.*css"])} ``` and so on ...

and it will print:
```
<link rel="stylesheet" type="text/css"  href="/magnoliaAuthor/travel/.resources/travel-demo-theme/css/travels-magnolia~2015-12-14-06-46-59-000~cache.css"/>
<link rel="stylesheet" type="text/css"  href="/magnoliaAuthor/travel/.resources/travel-demo-theme/libs/slick-carousel/css/slick-theme~2015-12-14-06-46-59-000~cache.css"/>
...
```

Similar functions exist also for Javascript:
```${hcmcfn.js("/travel-demo-theme.*js")} ```, ```${hcmcfn.js(["/travel-demo-theme.*js", ".*magnolia.*js"])} ```
and will print:
```
<script src="/magnoliaAuthor/travel/.resources/travel-demo-theme/js/html5shiv.js"></script>
<script src="/magnoliaAuthor/travel/.resources/travel-demo-theme/js/jquery-1.10.2.min.js"></script>
...
```

or cached:
```${hcmcfn.cachedJs("/travel-demo-theme.*js")} ``` respectively ```${hcmcfn.cachedJs(["/travel-demo-theme.*js", ".*magnolia.*js"])} ```

with output like:
```
<script src="/magnoliaAuthor/travel/.resources/travel-demo-theme/js/html5shiv~2015-12-14-06-46-59-000~cache.js"></script>
<script src="/magnoliaAuthor/travel/.resources/travel-demo-theme/js/jquery-1.10.2.min~2015-12-14-06-46-59-000~cache.js"></script>
...
```

License
-------

Released under the GPLv3, see LICENSE.txt.

Feel free to use this app, but if you modify the source code please fork us on Github.

Maven dependency
-----------------
```xml
    <dependency>
      <groupId>com.neatresults.mgnltweaks</groupId>
      <artifactId>neat-resources</artifactId>
      <version>1.0.1</version>
    </dependency>
```

Versions
-----------------
Version 1.0.x should be compatible with all Magnolia 5.x versions, but was tested only on 5.4.3 and not before. If you run into any issues w/ older versions, please report them back.

Latest version can be found at https://nexus.magnolia-cms.com/service/local/repositories/magnolia.forge.releases/content/com/neatresults/mgnltweaks/neat-resources/1.0.1/neat-resources-1.0.1.jar

Installation & updates
-----------------
Upon instalation, module will register templating functions class and expose it under name awesomefn under freemarker renderer. To run, module requires Java 8.


Changes
-----------------
1.0.1
- fixed module descriptor to ensure correct init.
- introduced de-duplication of outputed links when multiple patterns are used in single call.

1.0
- functions for printing links to css and js resources from within template.

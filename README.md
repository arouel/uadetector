UADetector
==========


What is UADetector?
-----

UADetector is a library to identify over 190 different desktop and mobile
browsers and 130 other User-Agents like feed readers, email clients and
multimedia players. In addition, even more than 400 robots like BingBot,
Googlebot or Yahoo Bot can be identified.

This library is a free, portable Java library to analyze User-Agent strings.
The goal of this library is to detect the type and the associated operating
system of a client like `Mobile Firefox 9.0` on `Android` or `Mobile
Safari 5.1` on `iOS`.

UADetector is divided into two modules. The core module includes
the API and implementation to read the detection information and the functions
to identify User-Agents. The resources module contains the database with the
necessary identification information and a service factory class to get simply
preconfigured UserAgentStringParser singletons. This library will be published
monthly, is integration-tested against the core module and is guaranteed to run
against the defined core.


Device categorization
-----

Since version `0.9.10` we support device categorization which means that for
instance an *iPhone* or *Nexus 4* will be classified as "Smartphone" and an
*iPad*, *Kindle* or *Surface RT* as "Tablet". Please take a look into our
[API documentation](http://uadetector.sourceforge.net/modules/uadetector-core/apidocs/net/sf/uadetector/ReadableUserAgent.html)
to get an idea what you can get when parsing an user agent string.


Features
--------

### Detects over 190 different browsers

This library detects over 190 different desktop and mobile browsers and 130
other User-Agents like feed readers, multimedia players and email clients.

### Identifies over 400 robots

In the Internet many robots are on their way to examine sites. A large number
of robots can be detected with this library.

### Monthly updated

Each month a new version of the resources module will be published so you can
always detect the latest User-Agents.

### Extremely tested

All classes in this library have been especially tested. The unit tests have
over 90% branch coverage and 98% line coverage. In addition, many integration
tests performed regularly.


How can You help?
-----------------

UADetector is an open source tool and welcomes contributions.

* Report bugs, feature requests and other issues in the
  [issue tracking](https://github.com/before/uadetector/issues) application, but look
  on our [known issues](http://uadetector.sourceforge.net/known-issues.html)
  page first before posting!
* Help with the documentation by pointing out areas that are lacking or
  unclear, and if you are so inclined, submitting patches to correct it. You
  can quickly contribute rough thoughts by forking this project on
  [GitHub](https://github.com/before/uadetector) and
  [SourceForge](http://sourceforge.net/p/uadetector/code/?branch=ref%2Fmaster),
  or you can volunteer to help collate and organize information that is already
  there.

Your participation in this project is much appreciated!


License
-------

Please visit the UADetector web site for more information:

  * [http://uadetector.sourceforge.net/](http://uadetector.sourceforge.net/)

Copyright 2012 André Rouél

André Rouél licenses this product to you under the Apache License, version 2.0
(the "License"); you may not use this product except in compliance with the
License. You may obtain a copy of the License at:

   [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied.  See the License for the
specific language governing permissions and limitations under the License.

Also, please refer to each LICENSE.*component*.txt file, which is located in
the same directory as this file, for the license terms of the components that
this product depends on.

-------------------------------------------------------------------------------
This product contains a modified version of Dave Koelle's Alphanum Algorithm,
which can be obtained at:

  * HOMEPAGE:
    * [http://www.davekoelle.com/alphanum.html](http://www.davekoelle.com/alphanum.html)
  * LICENSE:
    * LICENSE.alphanum.txt (GNU LGPL 2.1 or above)

This product uses a version of Jaroslav Mallat's UAS Data, which can be
obtained at:

  * HOMEPAGE:
    * [http://user-agent-string.info/](http://user-agent-string.info/)
  * LICENSE:
    * LICENSE.uas.txt (CC BY 3.0)

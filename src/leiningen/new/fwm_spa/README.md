# {{raw-name}}

This is an example project for a [ClojureScript](https://www.clojurescript.org) for an [SPA](https://en.wikipedia.org/wiki/Single-page_application) using [Reagent](https://reagent-project.github.io) and [figwheel-main](https://figwheel.org).

## Overview

This project was intended to serve as a learning experience in writing apps in ClojureScript using a [Clojure Deps](https://clojure.org/guides/deps_and_cli) style project and [figwheel-main](https://figwheel.org). It reflects the way that I often start my own projects.

Depending on how this project was created, it can also be built with [Leiningen]().

The project is inspired by the figwheel-main project template [here](https://github.com/bhauman/figwheel-main-template). It contains some small changes to the way of doing things that I prefer in my own projects.

What it produces is a simple SPA using Reagent to display the current time in a browser with regular, periodic visual updates. It's a trivial but educational example. Maybe it's a good starting point for an app that you've been planning to write.

## Development Builds

Of course, you need Java and Clojure to get started. I used Java 11 and Clojure 1.10.1.

From a terminal window in the project directory, get an interactive development environment by running:

    clj -A:fig:repl

or

    lein fig:repl

This will compile a development build, open a tab in the default browser at [`localhost:9500`](http://localhost:9500), and start a ClojureScript REPL in the terminal. Changes made to the ClojureScript portion of the project will be compiled and reloaded in real time. Changes affecting the browser display (Reagent components) will show up in the browser as well.

    clj -A:fig:dev

or

    lein fig:dev

Similar to the above but does not open a REPL.

## Production Build

To build an optimized and minimized version, run:

    clj -A:fig:min

or

    lein fig:min

You can run the resulting program by loading `resources/public/index.html` in your browser. The index file will load and run the minimized `resources/public/main.js` file.


## Testing

To do one-time ClojureScript testing, run:

    clj -A:fig:test

or

    lein fig:test

Test results will show up in the terminal. A testing web page will be opened, but you can just close it after the tests have completed. Or you can look at some of the notes in `test.cljs.edn` for a few ways to run the tests headless if you prefer.

If there are failing tests or other errors, a summary of test results with be displayed but the program will exit with a couple of stack traces too.

The project will also run automatic testing in the background when using the `:repl` and `:dev` aliases. Just open a browser tab at `http://localhost:9500/figwheel-extra-main/auto-testing` to see the results. The results will update shortly after any changes to the source files are saved.

## Other Aliases

To delete all compilation artifacts, use:

    clj -A:clean

or

    lein clean

To check for outdated dependencies, run:

    clj -A:ancient

or, if you have installed the [`lein-ancient`](https://github.com/xsc/lein-ancient) plugin

    lein ancient

## What it Does

The application itself is quite useless and silly. After loading up, the page will begin continually updating a display of the local time. It does so by asking for the system time every second then updating a Reagent atom with the new time. Changing the Reagent atom triggers the display change.

The program also keeps count of the number of times it updates the time and displays that on the screen.

At frequent intervals, the program will change the color used to display the time.

Congratulations! You have managed to turn your multiple hundred/thousand dollar system into a $10 clock.

## License

Copyright © 2020 David D. Clark

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

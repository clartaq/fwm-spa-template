# fwm-spa template

A template generator that will produce a minimal ClojureScript project that includes [Reagent](https://reagent-project.github.io) and [figwheel.main](https://figwheel.org) tooling.

This example follows closely the [figwheel-main template](https://github.com/bhauman/figwheel-main-template) by [Bruce Hauman](https://github.com/bhauman).

## Abridged Usage Examples

Already an expert? Can't stand to read more information? Assuming you have [lein](https://github.com/technomancy/leiningen) or [clj-new](https://github.com/seancorfield/clj-new) installed, you can use one of the following commands:

```shell
clj -A:new fwm-spa hello-world.core
```

or

```shell
lein new fwm-spa hello-world.core
```

## Overview

The `fwm-spa` template is intended to get you up and running
with a no-frills ClojureScript project initialized with the
ClojureScript Reagent framework. It is intended to work
equally well with
[Leiningen](https://github.com/technomancy/leiningen) or
[clj-new](https://github.com/seancorfield/clj-new).

## Usage

#### Using clj-new

Make sure you have
[installed the Clojure CLI tools](https://clojure.org/guides/getting_started#_clojure_installer_and_cli_tools)
and are running the latest version.

Also, ensure you have installed [clj-new](https://github.com/seancorfield/clj-new) as detailed [here](https://github.com/seancorfield/clj-new#getting-started) 
    
    clj -A:new fwm-spa hello-world.core

#### Using lein

Make sure you have the
[latest version of Leiningen installed](https://github.com/technomancy/leiningen#installation).

    lein new fwm-spa hello-world.core

### Options

Takes a **name** and possibly any number of **attribute** options of the form
`+attribute` and produces a minimal ClojureScript project that
includes Figwheel Main tooling

The initial **name** option is intended to be the name of your initial
project namespace. Here are some examples of valid project names:

    hello.core
    my.group/dominate.world

The attribute options are:

     +deps        which generates a deps.edn (a default when used with clj-new)
     +lein        which generates a project.clj (a default when used with lein)


# Compiling the generated project

### With a CLI tools project

To get an interactive development environment change into the project
root (the directory just created) and execute:

    clojure -A:fig:repl
    
After the compilation process is complete, and a browser has popped
open the compiled project in your browser, you will get a ClojureScript
REPL prompt that is connected to the browser.

An easy way to verify this is:

    cljs.user=> (js/alert "Am I connected?")

and you should see an alert in the browser window.

You can also supply arguments to `figwheel.main` like so:

    clojure -A:fig -b dev -r

To clean all compiled files:

    clj -A:clean

To check for up-to-date dependency versions:

    clj -A:ancient

To create a production build:

    clj -A:clean
    clojure -A:fig:min

### With Leiningen based project

To get an interactive development environment change into the project
root (the directory just created) and execute:

    lein fig:repl    

After the compilation process is complete, and a browser has popped
open the compiled project in your browser, you will get a ClojureScript
REPL prompt that is connected to the browser.   

An easy way to verify this is:

    cljs.user=> (js/alert "Am I connected?")

and you should see an alert in the browser window.

You can also supply arguments to `figwheel.main` like so:

    lein fig -- -b dev -r

To clean all compiled files:

    lein clean

To create a production build:

    lein clean
    lein fig:min

# Testing

Your initial tests are in the `test` directory. You will have tests
that are updated live with each file change if you open a tab to
`http://localhost:9500/figwheel-extra-main/auto-testing`

You can run your tests from the command line with:

    clj -A:fig:test    

or with `lein`:

    lein fig:test

## License

Copyright © 2020 David D. Clark

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

# Change Log
All notable changes to this project will be documented in this file. This change log follows the conventions of [keepachangelog.com](http://keepachangelog.com/).

## [Unreleased]
### Changed
- Add a new arity to `make-widget-async` to provide a different widget shape.

## [0.1.1] - 2020-08-03

### Added

- Added "RELEASE" to `project.clj` so that `clj-new` would work.
### Changed
- Documentation on how to make the widgets.

### Removed
- `make-widget-sync` - we're all async, all the time.

### Fixed

- Added the `.idea` directory to `.gitignore`.
- Fixed generation of the `gitignore` file.
- Expanded clean targets for `lein`based projects. widget maker to keep working when daylight savings switches over.

## 0.1.0 - 2020-08-03
### Added
- Files from the new template.

[Unreleased]: https://github.com/your-name/fwm-spa/compare/0.1.1...HEAD
[0.1.1]: https://github.com/your-name/fwm-spa/compare/0.1.0...0.1.1

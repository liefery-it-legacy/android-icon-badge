# Changelog

## 1.2.0

_2017-08-30_

 * Remove custom shadowing for the view
 * Implement ability to use android elevation on SDK version 21+
 * Badges can be exported with a custom elevation

## 1.1.1

_2017-08-16_

 * Allow arbitrary `Drawable`s in foreground shapes

## 1.1.0

_2017-08-08_

 *  Add pin background shape
 *  Make xml naming consistent

## 1.0.9

_2017-08-07_

 * Better Variable and Function Naming

## 1.0.8

_2017-08-04_

 * `StopBadgeView` no longer extends from `ImageView`
 * Removed ability to render path on Badge directly
 * Badges can now be square
 * Badge can be exported with alpha

## 1.0.7

_2017-06-30_

 * Fix Bitmap already recycled exception when changing `StopBadgeView` parameters after initial rendering

## 1.0.6

_2017-06-30_

 * Fix shadow drawing issue (being cut off)

## 1.0.5

_2017-06-15_

 * Properly invalidate the generated `Bitmap` after applying widget changes dynamically

## 1.0.4

_2017-06-14_

 * `StopBadgeView` now extends from `ImageView`
 * Upgrade to Android platform 26

## 1.0.3

_2017-06-13_

 * Draw plain circle when no shape is provided (fixes NPE)

## 1.0.2

_2017-06-08_

 *  Set support-annotations dependency to compile scope
 *  Change package from `stop.bade` to `stop_badge`

## 1.0.1

_2017-05-23_

 *  Add `setShapeArrowUp` and `setShapeArrowDown` to `StopBadge`

## 1.0.0

_2017-05-23_

 * Initial release

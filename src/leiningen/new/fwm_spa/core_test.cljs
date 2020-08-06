(ns {{namespace}}-test
  (:require
    [cljs.test :refer-macros [deftest is testing]]
    [{{namespace}} :refer [square]]))

(deftest passing-square
  (testing "The square function with passing results."
    (is (zero? (square 0)))
    (is (= 9 (square 3)))
    (is (= 4 (square -2)))))

(deftest failing-square
  (testing "The square function with failing results."
    (is (neg? (square -1)))))

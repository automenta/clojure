;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

;; Author: Gary Fredericks

(ns clojure.test-clojure.randomness
  (:use clojure.test)
  (:import [java.util Random]))

(deftest determinism
  (letfn [(distinct-count [f]
            (->> (repeatedly 100 f)
                 (distinct)
                 (count)))]
    (testing "rand is not deterministic by default"
      (is (< 1 (distinct-count rand))))
    (testing "rand is deterministic when seeded"
      (is (= 1 (distinct-count #(binding [*rand* (Random. 42)] (rand))))))
    (testing "shuffle is not deterministic by default"
      (is (< 1 (distinct-count #(shuffle [:a :b :c :d :e :f :g])))))
    (testing "shuffle is deterministic when seeded"
      (is (= 1 (distinct-count #(binding [*rand* (Random. 42)]
                                  (shuffle [:a :b :c :d :e :f :g]))))))))

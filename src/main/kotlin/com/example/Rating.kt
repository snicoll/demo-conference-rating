package com.example

class Rating(val conference: String, val rating: Int) {

    override fun toString(): String {
        return "Rating{" + "conference='" + conference + '\'' +
                ", rating=" + rating +
                '}'
    }

}

package com.mathy.contestprep

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random
private lateinit var sharedPrefs: SharedPreferences


class randQus : AppCompatActivity() {

    private lateinit var questionText: TextView
    private lateinit var nextQuestionButton: Button
    private lateinit var questionsList: ArrayList<String>
    private lateinit var shuffledQuestions: MutableList<String>
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rand_qus)

        sharedPrefs = getSharedPreferences("MathyPrefs", MODE_PRIVATE)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.action, theme)
        }

        // Connect views to their XML counterparts
        questionText = findViewById(R.id.questionText)
        nextQuestionButton = findViewById(R.id.nextQuestionButton)



        // Setup questions
        questionsList = arrayListOf(
                "Solve for x: x^2 + 6x + 9 = 0",
                "Find the sum of all positive divisors of 60.",
                "What is the largest integer less than or equal to sqrt(200)?",
                "In how many ways can 5 students be arranged in a row if 2 of them must be next to each other?",
                "The sum of two numbers is 25, and their difference is 5. What are the numbers?",
                "In a right triangle, the lengths of the legs are 9 and 12. What is the length of the hypotenuse?",
                "Solve for x: 3^(x+1) = 81",
                "A circle has a radius of 7 cm. What is the area of the circle in square centimeters?",
                "How many ways can you arrange the letters in the word 'MATHEMATICS'?",
                "If a triangle has angles of 45, 45, and 90 degrees, what is the length of the hypotenuse of a triangle with legs of length 10?",
                "A function f(x) is defined as f(x) = 2x^2 + 3x + 5. Find f(2).","What is the remainder when 7^103 is divided by 4?",
                "How many ways can 6 identical balls be placed into 3 distinct boxes?",
                "Find all integers x such that x^2 + 4x + 7 is a perfect square.",
                "The sum of three consecutive primes is 73. What are the primes?",
                "A triangle has side lengths 13, 14, and 15. What is its area?",
                "How many integers between 1 and 1000 are divisible by neither 2 nor 5?",
                "In a class of 30 students, every student shakes hands with every other. How many handshakes occur?",
                "Find the smallest positive integer n such that 2^n ≡ 1 mod 7.",
                "Simplify: (3x + 4)^2",
                "Solve for x: 5x − 3 = 2x + 9",
                "What is the number of trailing zeros in 1000! ?",
                "Find all x such that |x − 3| + |x − 5| = 6.",
                "If a + b = 10 and ab = 16, find a^2 + b^2.",
                "How many 4-digit numbers have all distinct digits?",
                "Prove that there is no integer solution to x^2 − 3y^2 = 2.",
                "A function f is defined as f(x) = x^3 − 6x^2 + 11x − 6. Find all values of x such that f(x) = 0.",
                "A circle is inscribed in a right triangle with legs 5 and 12. Find the radius of the circle.",
                "What is the probability that a randomly chosen divisor of 360 is a multiple of 6?",
                "Solve: (x + 1)/(x − 1) = (x − 1)/(x + 1)",
                "Let S be the set of all positive integers less than 100 that are not divisible by 2, 3, or 5. How many elements does S have?",
                "Let a, b, c be positive integers such that a + b + c = 30 and gcd(a, b, c) = 1. How many such triples exist?",
                "Find all real numbers x that satisfy: √(x + 5) + √(9 − x) = 7.",
                "Evaluate the sum: 1 − 2 + 3 − 4 + 5 − 6 + ... + 99 − 100.",
                "Determine all integers n such that n^2 + n + 1 is divisible by 7.",
                "A function f satisfies f(x + y) = f(x)f(y) for all real numbers x and y, and f(1) = 2. Find f(5).",
                "Find the number of positive integers less than 1000 that are divisible by neither 2, 3, nor 7.",
                "In a geometric sequence, the third term is 45 and the sixth term is 3645. What is the first term?",
                "Let a and b be real numbers such that a^2 + b^2 = 1 and ab = 1/2. Find a^4 + b^4.",
                "Find the number of integers between 1 and 1000 which are divisible by 3 or 5 but not both.",
                "A triangle has sides 13, 14, 15. Find the length of the altitude to the side of length 14.",
                "What is the coefficient of x^3 in the expansion of (2x − 1)^5?",
                "Find the number of ordered triples (x, y, z) of non-negative integers such that x + y + z = 10.",
                "Let S be the set {1, 2, 3, ..., 1000}. How many elements in S are divisible by 3 but not by 9?",
                "Solve for real x: (x − 3)(x − 2)(x − 1)(x)(x + 1)(x + 2)(x + 3) = 0.",
                "How many five-digit numbers have the sum of digits equal to 5?",
                "A bag contains 3 red, 4 blue, and 5 green balls. Two are drawn at random without replacement. What is the probability they are the same color?",
                "Solve the system: x + y + z = 6, x^2 + y^2 + z^2 = 14, xyz = 3.",
                "Find the smallest positive integer n such that 7n ≡ 1 mod 13.",
                "How many positive integers less than 1000 have exactly 3 divisors?",
                "Evaluate the sum: Σ (k=1 to 100) of floor(√k).",
                "How many solutions does the equation |x - 3| + |x - 7| = 10 have?",
                "Evaluate: (7^3 × 8^3)/(56^2).",
                "A number is divisible by both 6 and 15. What is the smallest such number greater than 100?",
                "Let S = 1 + 2 + 3 + ... + 99. What is the remainder when S is divided by 6?",
                "How many four-digit numbers contain at least one digit 7?",
                "Let a, b be integers such that a^2 + b^2 = 1000. How many such pairs (a, b) exist?",
                "The sum of two numbers is 50 and the difference of their squares is 300. Find the numbers.",
                "If 3^x = 81 and 3^y = 243, what is the value of x + y?",
                "A square and a circle have equal perimeters. Which has a larger area?",
                "The digits of a 3-digit number are reversed. The result is subtracted from the original. What's the largest possible positive difference?",
                "Let n be a positive integer such that 2^n < 500 < 2^(n+1). Find n.",
                "The polynomial x^3 + ax^2 + bx + c has 1 as a root. If the sum of its roots is 5, find a.",
                "In a group of 30 students, every student shakes hands with every other student. How many handshakes occur?",
                "How many positive integers less than 10000 have digits that are strictly increasing?",
                "The product of three positive integers is 72 and their sum is 19. What are the numbers?",
                "Let f(x) = x^3 - 3x + 1. Find all integer solutions to f(x) = 0.",
                "Two numbers differ by 4, and their product is 77. What are the numbers?",
                "A triangle has sides 5, 12, and 13. Find its area.",
                "If log₂(x) + log₂(x − 2) = 3, find x.",
                "Let A = {x ∈ ℕ | x < 100, x ≡ 1 mod 3 and x ≡ 2 mod 4}. How many elements are in A?",
                "Find the smallest prime greater than 100.",
                "How many integers between 1 and 100 are divisible by both 3 and 5?",
                "Solve for x: 2x + 3 = 5x − 6.",
                "If 4^x = 64, find x.",
                "What is the value of the expression: (5 × 6) − (3 × 4)?",
                "Simplify: (2x + 3)^2 − (2x − 3)^2.",
                "Factor completely: x^2 − 49.",
                "The area of a rectangle is 48 cm². If its length is 8 cm, find its width.",
                "Convert 0.125 into a fraction.",
                "What is the sum of the interior angles of a hexagon?",
                "The mean of five numbers is 20. If four of the numbers are 18, 22, 19, and 21, find the fifth.",
                "If f(x) = 3x² − x + 4, find f(−2).",
                "Expand and simplify: (x + 2)(x − 3).",
                "The product of two consecutive odd numbers is 255. Find the numbers.",
                "If sin x = 0.6 and cos x = 0.8, find tan x.",
                "A triangle has side lengths 7, 24, and 25. Determine if it is a right triangle.",
                "Let n be the smallest number such that 7n ends in 321. Find n.",
                "A bag contains 5 red, 3 green, and 2 blue balls. What is the probability of picking a red?",
                "How many distinct 4-digit numbers can be formed from the digits 1, 2, 3, 4 with no repetitions?",
                "If aₙ = 2aₙ₋₁ + 1 and a₁ = 1, find a₄.",
                "What is the value of x if 3x − 7 = 2x + 5?",
                "Find the HCF of 18 and 42.",
                "If 25% of a number is 15, what is the number?",
                "Solve: (x − 2)(x + 4) = 0.",
                "Evaluate: 2³ × 3².",
                "A car travels 120 km in 3 hours. What is its average speed?",
                "Find the next term: 2, 4, 8, 16, ...",
                "How many even numbers are between 1 and 50?",
                "A number is divisible by 6 if it is divisible by which two numbers?",
                "If 3 pencils cost 1200 UGX, what is the cost of 5 pencils?",
                "The sum of three consecutive integers is 72. Find the integers.",
                "Factor: x² + 5x + 6.",
                "Solve for x: 5(x − 1) = 2x + 7.",
                "What is the square root of 225?",
                "How many diagonals does a hexagon have?",
                "A school has 4 houses. In how many ways can 3 students be selected, one from each of any 3 houses?",
                "If 7! = 5040, find 6!.",
                "Find the remainder when 2^20 is divided by 7.",
                "A circle passes through the vertices of a triangle. What is the name of this circle?",
                "Find the smallest positive integer that leaves a remainder 1 when divided by 2, 3, and 4.",
                "Solve for x: 2x + 7 = 3x − 4.",
                "Find the product of all prime numbers between 10 and 20.",
                "What is the sum of the first 10 prime numbers?",
                "If the area of a circle is 49π square units, what is the radius?",
                "Find the value of x in the equation 5(x − 3) = 2(x + 4).",
                "Simplify: (4x² + 5x − 3) − (2x² − 3x + 7).",
                "A right triangle has legs of length 8 and 15. What is the length of the hypotenuse?",
                "What is the value of 2⁵ × 3²?",
                "How many different ways can you arrange the letters of the word 'REACT'?",
                "How many integer solutions exist for the equation x² − 6x + 8 = 0?",
                "Find the area of a trapezoid with bases 5 cm and 9 cm, and height 6 cm.",
                "What is the greatest common divisor (GCD) of 48 and 180?",
                "Find the perimeter of a square whose side length is 12 cm.",
                "What is the sum of the first 50 odd numbers?",
                "A train travels at a speed of 80 km/h. How far will it travel in 3.5 hours?",
                "If a rectangle has a length of 8 cm and a width of 3 cm, what is its diagonal?",
                "Find the roots of the quadratic equation: x² − 7x + 10 = 0.",
                "Calculate the volume of a cylinder with radius 4 cm and height 10 cm.",
                "If f(x) = x² + 3x + 2, find f(3).",
                "What is the value of the determinant of the matrix [[1, 2], [3, 4]]?",
                "Find the sum of the angles in a nonagon.",
                "Solve for x: 3x − 5 = 2(x + 4).",
                "What is the sum of the interior angles of a hexagon?",
                "If the area of a triangle is 36 square units, and its base is 6 units, what is its height?",
                "Find the sum of the first 25 even numbers.",
                "How many different ways can you choose 3 items from a set of 8 items?",
                "Solve for x: 5(x + 3) = 2(x + 7).",
                "Find the least common multiple (LCM) of 12 and 18.",
                "If 3x − 7 = 11, what is x?",
                "Calculate the area of a circle with a radius of 5 cm.",
                "Find the value of x in the equation 4x − 9 = 2x + 5.",
                "Find the volume of a cube with side length 6 cm.",
                "How many factors does 36 have?",
                "Solve for x: 2x² − 3x − 5 = 0.",
                "A box contains 12 red balls, 15 blue balls, and 18 green balls. What is the probability of drawing a blue ball?",
                "What is the area of a rhombus with diagonals 8 cm and 10 cm?",
                "If the sum of two consecutive even numbers is 28, what are the numbers?",
                "Simplify: (3x² + 4x − 5) + (x² − 2x + 3).",
                "Find the value of x if 2x − 3 = 7 − 3x.",
                "If the ratio of the angles of a triangle is 3:4:5, what are the measures of the angles?",
                "Find the perimeter of a rectangle with length 8 cm and width 5 cm.",
                "Solve for x: 2x + 3 = 5x − 12.",
                "Calculate the area of a triangle with base 12 cm and height 9 cm.",
                "What is the value of π to 3 decimal places?",
                "Find the square root of 169.",
                "If the radius of a sphere is 4 cm, what is its volume? (Use 3.14 for π)",
                "How many diagonals does a decagon have?",
                "Calculate the length of the hypotenuse of a right triangle with legs of length 6 cm and 8 cm.",
                "Simplify: (2x + 3)².",
                "Solve for x: 5(x + 2) − 3 = 2(x − 1).",
                "What is the value of 4³?",
                "If a car travels 60 km in 1 hour, how far will it travel in 5 hours at the same speed?",
                "What is the sum of the first 10 odd numbers?",
                "Find the value of x in the equation 2x + 7 = 3x − 9.",
                "If the area of a square is 64 square cm, what is its side length?",
                "Solve for x: 3(x − 4) + 5 = 2(x + 6).",
                "What is the cube root of 216?",
                "How many ways can 4 people be arranged in a row?",
                "What is the value of 2² + 3²?",
                "Find the volume of a cylinder with radius 3 cm and height 10 cm.",
                "Let f(x) = x³ + 3x² + 3x + 1. Find the value of f(−1) without direct substitution.",
                "Prove that the number of divisors of 72 is greater than 10 and find the exact count.",
                "Solve the Diophantine equation: x² − 2xy + y² = 1 for integers x and y.",
                "Find the largest integer that divides both 1234567 and 7654321.",
                "Evaluate the integral: ∫(from 0 to π) cos²(x) dx.",
                "Let n be an integer. Prove that if n² + 4 is a perfect square, then n is even.",
                "Find the sum of the series: 1 + 3 + 5 + ... + 2019.",
                "Prove that for any prime number p, p² + 1 is not divisible by p.",
                "Find the sum of the infinite series: 1 + 2 + 3 + 4 + ... up to infinity (in the extended sense).",
                "Given a function f(x) = x² − 2x + 5, find the inverse of the function and its domain.",
                "Find the sum of all integers n for which n² + 5n + 6 is a perfect square.",
                "Determine the largest integer that divides 12! but does not divide 13!.",
                "Solve for x: 4^(x+1) = 64^(x−2).",
                "Prove that the sum of the first n odd integers is always a perfect square.",
                "Find the number of integer solutions to the equation x² + 4x + 3 = 0 in Z.",
                "Given a triangle with sides of length a, b, and c, find the area of the triangle using Heron’s formula.",
                "Let S = 1 + 2 + 3 + ... + 100. Find the remainder when S is divided by 9.",
                "Prove that the square of any odd number is congruent to 1 mod 8.",
                "Find the number of distinct solutions to the equation x² + y² = 100 for integers x and y.",
                "Find the value of the limit: lim (n → ∞) (1 + 1/n)ⁿ.",
                "Find the maximum value of x in the inequality 3x + 7 ≤ 2x + 12.",
                "Prove that for any integer n, n³ − n is divisible by 6.",
                "Prove that the sum of the squares of the first n integers is divisible by 3 if and only if n is divisible by 3.",
                "Solve the equation: 2x² − 5x + 3 = 0 over the complex numbers.",
                "Let A and B be two sets. Prove that |A ∪ B| + |A ∩ B| = |A| + |B|.",
                "Find the sum of the first 50 terms of the sequence defined by aₙ = 2n + 3.",
                "Prove that there are no integer solutions to the equation x³ + y³ = z³ + 6.",
                "Let p be a prime number. Show that p² + 1 is never divisible by p for any prime p.",
                "Find the smallest integer greater than 1000 that is divisible by 5 and 7 but not by 3.",
                "Prove that for every integer n, n² − 3 is divisible by 8 if and only if n is odd.",
                "Solve for x: log₅(x) = 2.",
                "Solve the equation: x³ − 6x² + 9x = 0 over the complex numbers.",
                "Find the sum of the infinite geometric series: 1 + 1/2 + 1/4 + 1/8 + ...",
                "Find the largest integer that divides both 9876543 and 1234567.",
                "Solve for x: (x − 1)² + (x + 2)² = 25.",
                "Prove that the sum of the first n cubes is equal to the square of the sum of the first n integers.",
                "Find the smallest positive integer that satisfies the congruence: 7x ≡ 1 (mod 13).",
                "Prove that for all integers n, n² − 3n is divisible by 6.",
                "Solve the equation: x⁴ − 6x² + 9 = 0.",
                "Solve for x: 5x² + 6x − 7 = 0.",
                "Find the number of distinct real solutions to the equation x³ − 5x² + 4x = 0.",
                "Find the value of the limit: lim (n → ∞) (1 − 1/n)ⁿ.",
                "Solve for x: log₄(x) + log₄(x − 3) = 2.",
                "Prove that the sum of the squares of the first n odd numbers is always divisible by 3.",
                "Find the number of distinct solutions to the equation 4x − 7 = 2x + 1.",
                "Solve the equation: 2x³ − 5x² + 3x − 7 = 0.",
                "Solve for x: e^(2x) = 5.",
                "Find the value of the integral: ∫(from 0 to 1) x² e^x dx.",
                "Prove that the sum of the first n squares is always divisible by 3 if n is divisible by 3.",
                "Given the function f(x) = x² − 3x + 5, find the range of f(x).",
                "Find the number of distinct integer solutions to the equation x² − 5x + 6 = 0.",
                "Prove that for any integer n, n³ + n is divisible by 6.",
                "Find the value of the infinite sum: 1 + 1/2 + 1/3 + 1/4 + ... up to infinity.",
                "Prove that there are no integer solutions to the equation x³ + y³ = z³ + 5.",
                "Find the sum of the series: 1 + 1/2 + 1/4 + 1/8 + ... up to infinity.",
                "Find all pairs of integers (x, y) such that x² + y² = 100.",
                "Prove that for every integer n, n² − 1 is divisible by 3.",
                "Find the number of solutions to the equation x² + y² + z² = 3 in integers.",
                "Evaluate the sum of the infinite series: 1 − 1/2 + 1/4 − 1/8 + 1/16 − ...",
                "Given the equation x³ − 4x² + 5x − 2 = 0, find the roots of the equation.",
                "Let f(x) = 2x² − 3x + 4. Find the maximum value of f(x) for x ∈ [0, 2].",
                "Prove that for any prime p > 3, p² ≡ 1 (mod 24).",
                "Solve the equation 3x + 2y = 10 for integer solutions.",
                "Find the number of distinct integer solutions to the equation x² + y² = 50.",
                "Solve the equation: 2^(x+1) = 5^(x−2).",
                "Let A be a 2×2 matrix such that A² = I. Show that A is either the identity matrix or the negative identity matrix.",
                "Find the largest prime factor of 100!.",
                "Prove that if x and y are positive integers such that x² + y² = 2019, then x and y must both be odd.",
                "Let S = {1, 2, 3, ..., 2019}. How many ways are there to choose a subset of S such that the sum of the elements in the subset is divisible by 3?",
                "Find the number of distinct integer solutions to the equation x³ + y³ = 1729.",
                "Prove that the sum of the first n odd numbers is equal to n² for all positive integers n.",
                "Evaluate the integral ∫(from 0 to ∞) e^(-x²) dx.",
                "Find the number of divisors of 1440.",
                "Given a quadratic equation ax² + bx + c = 0, find the discriminant and determine the nature of the roots.",
                "Find all integer solutions to the equation x² − 4y = 1.",
                "Given the sequence defined by aₙ = 2aₙ₋₁ + aₙ₋₂, find the first 10 terms if a₀ = 1 and a₁ = 2.",
                "Solve for x: 5x³ + 6x² − 7x = 0.",
                "Find the largest integer that divides both 1349 and 2521.",
                "Solve the equation 2x³ − 9x² + 7x − 1 = 0.",
                "Let A = {1, 2, 3, ..., 1000}. How many distinct subsets of A can be formed such that the sum of the elements is divisible by 7?",
                "Prove that for any integer n, n³ − 5n is divisible by 6.",
                "Solve the equation: 3^(2x + 1) = 27^(x − 2).",
                "Prove that for any positive integer n, n² − 3n is divisible by 6.",
                "Find the sum of the squares of the first 100 prime numbers.",
                "Solve for x: log₂(x) + log₂(x − 4) = 3.",
                "Let p be a prime number. Prove that p² + 1 is never divisible by p.",
                "Find the number of distinct integer solutions to the equation x³ + y³ + z³ = 33.",
                "Solve for x: 2^(x+1) + 3^(x+1) = 5^(x+1).",
                "Let f(x) = x² − 2x + 5. Find the inverse function of f(x).",
                "Evaluate the sum of the series: 1 + 1/2 + 1/4 + 1/8 + ... up to infinity.",
                "Let A and B be two sets. Prove that |A ∪ B| + |A ∩ B| = |A| + |B|.",
                "Find the number of solutions to the equation x² + y² = 25 in integers.",
                "Prove that for any integer n, n² − 3 is divisible by 8 if and only if n is odd.",
                "Let f(x) = 3x² − 2x + 1. Find the maximum value of f(x) for x ∈ [−1, 1].",
                "Solve for x: 5x² − 6x + 3 = 0.",
                "Find the number of divisors of 1024.",
                "Solve the equation: x² − 4x + 4 = 0.",
                "Prove that the product of two consecutive integers is always divisible by 2.",
                "Find the number of integer solutions to the equation x² + 2x − 8 = 0.",
                "Given the sequence aₙ = n² + 3n, find the sum of the first 10 terms.",
                "Solve the equation: 3^(x + 2) = 9^(x − 1).",
                "Find the maximum value of the function f(x) = x³ − 4x² + 3x − 2 for x ∈ [0, 3].",
                "Find the value of the integral ∫(from 0 to 1) x³ e^x dx.",
                "Prove that the sum of the squares of the first n odd integers is divisible by 3.",
                "Find the number of solutions to the equation 2x + 3y = 20 in integers.",
                "Find the number of distinct solutions to the equation x² + y² = 100 for integers x and y.",
                "Find the number of divisors of the number 1000.",
                "Solve the equation: x³ − 3x² + 4x − 5 = 0.",
                "Prove that if x is an integer, then x² + 5x + 6 is divisible by 6.",
                "Solve for x: 7x² − 5x − 6 = 0.",
                "Find the sum of all divisors of 72.",
                "Given the quadratic equation x² − 2x + 1 = 0, find the roots.",
                "Solve the equation: x² − 2x − 3 = 0 for x.",
                "Find the greatest common divisor of 132 and 198.",
                "Find the sum of the cubes of the first 10 prime numbers.",
                "Prove that for any integer n, n³ − n is divisible by 6.",
                "Find the roots of the quadratic equation x² + 4x + 3 = 0.",
                "Find the sum of the first 50 terms of the arithmetic sequence: 3, 6, 9, ...",
                "Prove that the number of divisors of 120 is greater than 10.",
                "Find the number of distinct integer solutions to the equation x³ + y³ = 1729.",
                "Prove that the sum of the first n cubes is equal to the square of the sum of the first n integers.",
                "Solve for x: log₅(x + 3) = 2.",
                "Solve the equation: 2x³ − 9x² + 7x − 1 = 0.",
                "Solve for x: 4^(x+1) = 64^(x−2).",
                "Find the largest integer that divides both 9876543 and 1234567.",
                "Prove that for any prime p > 3, p² ≡ 1 (mod 24).",
                "Find all integer solutions to the equation x³ + y³ + z³ = 33.",
                "Prove that the sum of the squares of the first n odd numbers is equal to n² for all positive integers n.",
                "Let p be a prime number. Prove that p² + 1 is never divisible by p.",
                "Solve the equation: 3^(2x + 1) = 27^(x − 2).",
                "Let f(x) = x² − 2x + 5. Find the inverse function of f(x).",
                "Evaluate the sum of the series: 1 + 1/2 + 1/4 + 1/8 + ... up to infinity.",
                "Let A and B be two sets. Prove that |A ∪ B| + |A ∩ B| = |A| + |B|.",
                "Find the number of solutions to the equation x² + y² = 25 in integers.",
                "Solve for x: log₂(x) + log₂(x − 4) = 3.",
                "Prove that for any positive integer n, n³ − 5n is divisible by 6.",
                "Find the number of divisors of 1440.",
                "Prove that for any integer n, n² − 1 is divisible by 3.",
                "Find the largest integer that divides both 1349 and 2521.",
                "Given a quadratic equation ax² + bx + c = 0, find the discriminant and determine the nature of the roots.",
                "Find all integer solutions to the equation x² − 4y = 1.",
                "Solve for x: 5x³ + 6x² − 7x = 0.",
                "Given the sequence defined by aₙ = 2aₙ₋₁ + aₙ₋₂, find the first 10 terms if a₀ = 1 and a₁ = 2.",
                "Solve for x: 2^(x+1) = 5^(x−2).",
                "Prove that for any prime p > 3, p² ≡ 1 (mod 24).",
                "Solve for x: 2x³ − 9x² + 7x − 1 = 0.",
                "Find the sum of the first 100 prime numbers.",
                "Let A be a 2×2 matrix such that A² = I. Show that A is either the identity matrix or the negative identity matrix.",
                "Find the largest prime factor of 100!.",
                "Let S = {1, 2, 3, ..., 2019}. How many ways are there to choose a subset of S such that the sum of the elements in the subset is divisible by 3?",
                "Prove that for any integer n, n² − 3 is divisible by 8 if and only if n is odd.",
                "Solve the equation 5x + 3y = 18 for integer solutions.",
                "Solve the equation: x³ − 3x² + 4x − 5 = 0.",
                "Let f(x) = 3x² − 2x + 1. Find the maximum value of f(x) for x ∈ [−1, 1].",
                "Prove that for any integer n, n³ − n is divisible by 6.",
                "Evaluate the integral ∫(from 0 to ∞) e^(-x²) dx.",
                "Solve the equation: x³ − 5x² + 7x − 11 = 0.",
                "Prove that for any integer n, n² − 3 is divisible by 8 if and only if n is odd.",
                "Find the number of divisors of 1024.",
                "Find the value of the sum: 1 + 2 + 3 + ... + 1000.",
                "Let f(x) = x³ − 6x² + 11x − 6. Find the real roots of f(x) = 0.",
                "Find the number of integer solutions to the equation x² + y² + z² = 25.",
                "Solve for x: 5x³ − 6x² + 7x − 1 = 0.",
                "Prove that for any integer n, n² + 5n + 6 is divisible by 6.",
                "Solve the equation: x² − 4x + 4 = 0.",
                "Find the number of divisors of 1000.",
                "Find the sum of the cubes of the first 10 prime numbers.",
                "Solve the equation: x³ − 5x² + 3x − 1 = 0.",
                "Solve for x: 4^(x+1) = 64^(x−2).",
                "Solve for x: 7x² − 5x − 6 = 0.",
                "Find the sum of all divisors of 72.",
                "Find the greatest common divisor of 132 and 198.",
                "Find the number of distinct integer solutions to the equation x³ + y³ = 1729.",
                "Prove that the sum of the first n cubes is equal to the square of the sum of the first n integers.",
                "Prove that the product of two consecutive integers is always divisible by 2.",
                "Find the sum of the squares of the first 100 prime numbers.",
                "Solve the equation: 2x³ − 9x² + 7x − 1 = 0.",
                "Let f(x) = x² − 2x + 1. Find the roots of the equation f(x) = 0.",
                "Solve the equation: x² − 2x − 3 = 0.",
                "Let n be a positive integer. Prove that n² − 5n is divisible by 6.",
                "Find the largest prime factor of 1000.",
                "Solve for x: 3x² − 2x − 1 = 0.",
                "Find the number of solutions to the equation x² + y² + z² = 100.",
                "Let f(x) = 2x² − 3x + 4. Find the maximum value of f(x) for x ∈ [0, 2].",
                "Find the number of divisors of the number 2000.",
                "Prove that for every integer n, n² − 1 is divisible by 3.",
                "Solve for x: 3^(x+2) = 81^(x−1).",
                "Solve the equation: x³ − 3x² + 2x − 1 = 0.",
                "Find the number of distinct integer solutions to the equation x² + y² = 100.",
                "Prove that for any integer n, n³ − 7n is divisible by 6.",
                "Find the largest prime factor of 987654.",
                "Find the sum of the first 100 terms of the arithmetic sequence: 7, 14, 21, ...",
                "Prove that for any prime p > 3, p² ≡ 1 (mod 24).",
                "Evaluate the integral ∫(from 0 to ∞) e^(−x²) dx.",
                "Find the largest prime divisor of 200000.",
                "Solve for x: x³ − 3x² + 2x − 1 = 0.",
                "Find the number of integer solutions to the equation x² + y² = 50.",
                "Let A be a matrix such that A² = I. Prove that A is either the identity matrix or the negative identity matrix.",
                "Find the number of divisors of 1024.",
                "Prove that the sum of the first n squares of odd numbers is always divisible by 3.",
                "Solve for x: 2^(x+1) − 5^(x−2) = 3.",
                "Let f(x) = x³ − 4x² + 5x − 2. Find the roots of the equation f(x) = 0.",
                "Prove that for any prime p, p² − 1 is divisible by 24.",
                "Find the number of solutions to the equation x² + y² = 45 for integers x and y.",
                "Find the sum of the cubes of the first 20 prime numbers.",
                "Solve for x: x² − 4x + 3 = 0.",
                "Prove that the number of divisors of any perfect square is odd.",
                "Find the largest prime factor of 2500."











        )


        // Restore state
        val savedIndex = sharedPrefs.getInt("CURRENT_INDEX", 0)
        val savedOrder = sharedPrefs.getString("QUESTION_ORDER", null)

        shuffledQuestions = if (savedOrder != null) {
            savedOrder.split("||").toMutableList()
        } else {
            questionsList!!.shuffled().toMutableList()
        }

        currentIndex = savedIndex
        // Set listener for the next button
        nextQuestionButton.setOnClickListener {
            loadRandomQuestion()
        }

        // First question on load
        loadRandomQuestion()
    }

    private fun loadRandomQuestion() {
        if (currentIndex >= shuffledQuestions.size) {
            shuffledQuestions = questionsList!!.shuffled().toMutableList()
            currentIndex = 0
        }

        questionText!!.text = shuffledQuestions[currentIndex]

        // Save current state
        val editor = sharedPrefs.edit()
        editor.putInt("CURRENT_INDEX", currentIndex + 1)
        editor.putString("QUESTION_ORDER", shuffledQuestions.joinToString("||"))
        editor.apply()

        currentIndex++
    }
}

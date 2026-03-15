package com.mathy.contestprep

import android.content.Context

fun buildLadderItems(
    context: Context,
    db: DBhandler
): MutableList<LadderItem> {
    return mutableListOf(
        LadderItem(
            key = "one",
            imageRes = R.drawable.capture2_edited,
            backgroundColorRes = R.color.one,
            prompt = "Select an answer below",
            answerLabel = "ANS = yes",
            inputType = LadderInputType.Choice(
                listOf("yes, it exists", "No, it doesn't")
            ),
            acceptedAnswers = listOf("yes, it exists")
        ),
        LadderItem(
            key = "two",
            imageRes = R.drawable.capture_edited,
            backgroundColorRes = R.color.two,
            prompt = "Enter the smallest number of spiders below",
            answerLabel = "ANS = 50",
            inputType = LadderInputType.SingleInput("Put Here!!", InputMode.NUMBER),
            acceptedAnswers = listOf("50")
        ),
        LadderItem(
            key = "three",
            imageRes = R.drawable.screenshot_2024_05_01_031247_edited,
            backgroundColorRes = R.color.three,
            prompt = "Select answer below",
            answerLabel = "ANS = yes",
            inputType = LadderInputType.Choice(
                listOf("Yes, it is", "No, it is not")
            ),
            acceptedAnswers = listOf("Yes, it is")
        ),
        LadderItem(
            key = "four",
            imageRes = R.drawable.screenshot_2024_05_01_031416_edited,
            backgroundColorRes = R.color.four,
            prompt = "Enter the number of arrangements below.",
            answerLabel = "ANS = 34",
            inputType = LadderInputType.SingleInput("Put Number here!!", InputMode.NUMBER),
            acceptedAnswers = listOf("34")
        ),
        LadderItem(
            key = "five",
            imageRes = R.drawable.screenshot_2024_05_03_081642_edited,
            backgroundColorRes = R.color.five,
            prompt = "I don't like football!!",
            answerLabel = "ANS = 5",
            inputType = LadderInputType.SingleInput("Here!!", InputMode.NUMBER),
            acceptedAnswers = listOf("5")
        ),
        LadderItem(
            key = "six",
            imageRes = R.drawable.screenshot_2024_05_01_031530_edited,
            backgroundColorRes = R.color.six,
            prompt = "Enter values of N below one after the other.",
            answerLabel = "ANS = 4",
            inputType = LadderInputType.SingleInput("Number !!!", InputMode.NUMBER),
            acceptedAnswers = listOf("4")
        ),
        LadderItem(
            key = "seven",
            imageRes = R.drawable.screenshot_2024_05_01_031551_edited,
            backgroundColorRes = R.color.seven,
            prompt = "Put the ratio below",
            answerLabel = "ANS = 1:4:5",
            inputType = LadderInputType.TripleInput(),
            acceptedAnswers = listOf("145", "154", "415", "451", "514", "541")
        ),
        LadderItem(
            key = "eight",
            imageRes = R.drawable.screenshot_2024_05_01_031615_edited,
            backgroundColorRes = R.color.eight,
            prompt = "Enter the possible number below",
            answerLabel = "ANS = 3",
            inputType = LadderInputType.SingleInput("Put here!!!", InputMode.NUMBER),
            acceptedAnswers = listOf("3")
        ),
        LadderItem(
            key = "nine",
            imageRes = R.drawable.screenshot_2024_05_03_081410_edited,
            backgroundColorRes = R.color.nine,
            prompt = "Let the answer land below",
            answerLabel = "ANS = 240",
            inputType = LadderInputType.SingleInput("Here!!", InputMode.NUMBER),
            acceptedAnswers = listOf("240")
        ),
        LadderItem(
            key = "ten",
            imageRes = R.drawable.screenshot_2024_05_01_031748_edited,
            backgroundColorRes = R.color.ten,
            prompt = "what's your largest value of k?",
            answerLabel = "ANS = 8",
            inputType = LadderInputType.SingleInput("Place here!!!", InputMode.NUMBER),
            acceptedAnswers = listOf("8")
        ),
        LadderItem(
            key = "eleven",
            imageRes = R.drawable.screenshot_2024_05_03_081333_edited,
            backgroundColorRes = R.color.eleven,
            prompt = "Just like an alien eye. What percentage of the design is black?",
            answerLabel = "ANS = 41.67",
            inputType = LadderInputType.SingleInput("Here!!", InputMode.DECIMAL),
            acceptedAnswers = listOf("41.67")
        ),
        LadderItem(
            key = "twelve",
            imageRes = R.drawable.screenshot_2024_05_01_031917_edited,
            backgroundColorRes = R.color.twelve,
            prompt = "Be sure about what you are about to select.",
            answerLabel = "ANS = yes",
            inputType = LadderInputType.Choice(
                listOf("Yes, it is", "No, it isn't")
            ),
            acceptedAnswers = listOf("Yes, it is")
        ),
        LadderItem(
            key = "thirteen",
            imageRes = R.drawable.screenshot_2024_05_01_031939_edited,
            backgroundColorRes = R.color.thirteen,
            prompt = "Enter the fraction as a decimal to two decimal places.",
            answerLabel = "ANS = 0.94",
            inputType = LadderInputType.SingleInput("Don't skip. Here!!", InputMode.DECIMAL),
            acceptedAnswers = listOf("0.94")
        ),
        LadderItem(
            key = "fourteen",
            imageRes = R.drawable.screenshot_2024_05_01_032347_edited,
            backgroundColorRes = R.color.fourteen,
            prompt = "Enter the probability to two decimal places below",
            answerLabel = "ANS = 0.06",
            inputType = LadderInputType.SingleInput("Place here!!", InputMode.DECIMAL),
            acceptedAnswers = listOf("0.06")
        ),
        LadderItem(
            key = "fifteen",
            imageRes = R.drawable.screenshot_2024_05_01_032000_edited,
            backgroundColorRes = R.color.fifteen,
            prompt = "You like angles? Let's see whether you got that one.",
            answerLabel = "ANS = 540",
            inputType = LadderInputType.SingleInput("Enter here", InputMode.NUMBER),
            acceptedAnswers = listOf("540")
        ),
        LadderItem(
            key = "sixteen",
            imageRes = R.drawable.screenshot_2024_05_03_081100_edited,
            backgroundColorRes = R.color.sixteen,
            prompt = "Just some digits? isn't it?",
            answerLabel = "ANS = 7",
            inputType = LadderInputType.SingleInput("Place here!!", InputMode.NUMBER),
            acceptedAnswers = listOf("7")
        ),
        LadderItem(
            key = "seventeen",
            imageRes = R.drawable.screenshot_2024_05_01_032913_edited,
            backgroundColorRes = R.color.seventeen,
            prompt = "Even ChatGPT can do it. Let's go!",
            answerLabel = "ANS = 15",
            inputType = LadderInputType.SingleInput("Place here!!", InputMode.NUMBER),
            acceptedAnswers = listOf("15")
        ),
        LadderItem(
            key = "eighteen",
            imageRes = R.drawable.screenshot_2024_05_01_032955_edited,
            backgroundColorRes = R.color.eighteen,
            prompt = "You could literally count them by the way!",
            answerLabel = "ANS = 188",
            inputType = LadderInputType.SingleInput("Place here!!", InputMode.NUMBER),
            acceptedAnswers = listOf("188")
        ),
        LadderItem(
            key = "nineteen",
            imageRes = R.drawable.screenshot_2024_05_01_033252_edited,
            backgroundColorRes = R.color.nineteen,
            prompt = "Write the fraction in two decimal places",
            answerLabel = "ANS = 0.33",
            inputType = LadderInputType.SingleInput("Here!!", InputMode.DECIMAL),
            acceptedAnswers = listOf("0.33")
        ),
        LadderItem(
            key = "twenty",
            imageRes = R.drawable.screenshot_2024_05_03_081710_edited,
            backgroundColorRes = R.color.twenty,
            prompt = "I think you like these numbers. There are two easy solutions. Can you get all?",
            answerLabel = "ANS = 28",
            inputType = LadderInputType.SingleInput("Here!! for all", InputMode.NUMBER),
            acceptedAnswers = listOf("28", "24")
        ),
        LadderItem(
            key = "twentyone",
            imageRes = R.drawable.screenshot_2024_05_01_033424_edited,
            backgroundColorRes = R.color.twentyone,
            prompt = "It's just the dots we need.",
            answerLabel = "ANS = 105",
            inputType = LadderInputType.SingleInput("Here!!", InputMode.NUMBER),
            acceptedAnswers = listOf("105")
        ),
        LadderItem(
            key = "twentytwo",
            imageRes = R.drawable.screenshot_2024_05_01_033449_edited,
            backgroundColorRes = R.color.twentythree,
            prompt = "The first is 10. Pretty simple I guess.",
            answerLabel = "ANS = 22",
            inputType = LadderInputType.SingleInput("Here!!", InputMode.NUMBER),
            acceptedAnswers = listOf("22")
        ),
        LadderItem(
            key = "twentythree",
            imageRes = R.drawable.screenshot_2024_05_01_033533_edited,
            backgroundColorRes = R.color.twentyfour,
            prompt = "Enter your digits in order below",
            answerLabel = "ANS = 784913526",
            inputType = LadderInputType.SingleInput("Here please!!", InputMode.NUMBER),
            acceptedAnswers = listOf("784913526")
        ),
        LadderItem(
            key = "twentyfour",
            imageRes = R.drawable.screenshot_2024_05_01_033609_edited,
            backgroundColorRes = R.color.twentyfive,
            prompt = "Enter answer below",
            answerLabel = "ANS = 100",
            inputType = LadderInputType.SingleInput("The paid version!!!", InputMode.NUMBER),
            acceptedAnswers = listOf("100")
        ),
        LadderItem(
            key = "twentyfive",
            imageRes = R.drawable.screenshot_2024_05_01_033656_edited,
            backgroundColorRes = R.color.twentysix,
            prompt = "It's a common error. Let's see whether you caught it",
            answerLabel = "ANS = 144",
            inputType = LadderInputType.SingleInput("Here!!", InputMode.NUMBER),
            acceptedAnswers = listOf("144")
        ),
        LadderItem(
            key = "twentysix",
            imageRes = R.drawable.screenshot_2024_05_01_033738_edited,
            backgroundColorRes = R.color.twentyseven,
            prompt = "Only thing we know is that it is rational. Can you find it?",
            answerLabel = "ANS = 3",
            inputType = LadderInputType.SingleInput("Put here!!", InputMode.DECIMAL),
            acceptedAnswers = listOf("3")
        )
    )
}
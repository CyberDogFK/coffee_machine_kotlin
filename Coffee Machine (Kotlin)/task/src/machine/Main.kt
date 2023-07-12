package machine

private const val WATER_ML_FOR_ONE_PORTION: Int = 200
private const val MILK_ML_FOR_ONE_PORTION: Int = 50
private const val COFFEE_BEANS_GRAMS_ONE_PORTION: Int = 15

class CoffeeMachine(
    private var watterSuppliesMl: Int,
    private var milkSuppliesMl: Int,
    private var coffeeBeansSuppliesGram: Int,
    private var disposableCupsSupplies: Int,
    private var totalAmountOfMoney: Int,
) {
    enum class MachineState {
        ENTRY {
            override fun printMessage() {
            }
        },
        ACTION_MENU {
            override fun printMessage() {
                println("Write action (buy, fill, take, remaining, exit):")
            }
        },
        BUY_MENU {
            override fun printMessage() {
                println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
            }
        },
        FILL_WATTER {
            override fun printMessage() {
                println("Write how many ml of water you want to add:")
            }
        },
        FILL_MILK {
            override fun printMessage() {
                println("Write how many ml of milk you want to add:")
            }
        },
        FILL_COFFEE {
            override fun printMessage() {
                println("Write how many grams of coffee beans you want to add:")
            }
        },
        FILL_CUPS {
            override fun printMessage() {
                println("Write how many disposable cups you want to add:")
            }
        },
        EXIT {
            override fun printMessage() {
            }
        };

        abstract fun printMessage();
    }

    private var currentMachineState: MachineState = MachineState.ENTRY

    fun readUserInput(input: String): Boolean {
        currentMachineState = when (currentMachineState) {
            MachineState.ENTRY -> MachineState.ACTION_MENU
            MachineState.ACTION_MENU -> action(input)
            MachineState.BUY_MENU -> buy(input)
            MachineState.FILL_WATTER -> fillWater(input)
            MachineState.FILL_MILK -> fillMilk(input)
            MachineState.FILL_COFFEE -> fillCoffe(input)
            MachineState.FILL_CUPS -> fillCups(input)
            else -> {
                println("Illegal state")
                MachineState.ACTION_MENU
            }
        }
        currentMachineState.printMessage()
        return currentMachineState != MachineState.EXIT
    }

    private fun action(input: String): MachineState {
        return when (input) {
            "buy" -> {
                MachineState.BUY_MENU
            }
            "fill" -> {
                MachineState.FILL_WATTER
            }
            "take" -> take()
            "remaining" -> printSupplies()
            "exit" -> {
                MachineState.EXIT
            }
            else -> {
                println("Illegal argument")
                MachineState.ACTION_MENU
            }
        }
    }

    private fun take(): MachineState {
        println("I gave you $totalAmountOfMoney")
        totalAmountOfMoney = 0
        return MachineState.ACTION_MENU
    }

    private fun fillWater(input: String): MachineState {
        watterSuppliesMl += input.toInt(10)
        return MachineState.FILL_MILK
    }

    private fun fillMilk(input: String): MachineState {
        milkSuppliesMl += input.toInt(10)
        return MachineState.FILL_COFFEE
    }

    private fun fillCoffe(input: String): MachineState {
        coffeeBeansSuppliesGram += input.toInt(10)
        return MachineState.FILL_CUPS
    }

    private fun fillCups(input: String): MachineState {
        disposableCupsSupplies += input.toInt(10)
        return MachineState.ACTION_MENU
    }

    private fun buy(input: String): MachineState {
        when (input) {
            "1" -> makeCoffee(250, 16, 0, 4)
            "2" -> makeCoffee(350, 20, 75, 7)
            "3" -> makeCoffee(200, 12, 100, 6)
            "back" -> {}
        }
        return MachineState.ACTION_MENU
    }

    private fun makeCoffee(
        waterForCoffee: Int,
        coffeeBeansForCoffee:Int,
        milkForCoffeeMl: Int,
        cost: Int
    ) {
        when {
            waterForCoffee > watterSuppliesMl -> println("Sorry, not enough water!")
            coffeeBeansForCoffee > coffeeBeansSuppliesGram -> println("Sorry, not enough coffee beans!")
            milkForCoffeeMl > milkSuppliesMl -> println("Sorry, not enough milk!")
            disposableCupsSupplies < 1 -> println("Sorry, not enough disposable cups!")
            else -> {
                println("I have enough resources, making you a coffee!")
                watterSuppliesMl -= waterForCoffee
                coffeeBeansSuppliesGram -= coffeeBeansForCoffee
                milkSuppliesMl -= milkForCoffeeMl
                totalAmountOfMoney += cost
                disposableCupsSupplies--
            }
        }
    }

    private fun printSupplies(): MachineState {
        println("The coffee machine has:")
        println("$watterSuppliesMl ml of water")
        println("$milkSuppliesMl ml of milk")
        println("$coffeeBeansSuppliesGram g of coffee beans")
        println("$disposableCupsSupplies disposable cups")
        println("$$totalAmountOfMoney of money")
        return MachineState.ACTION_MENU
    }
}

fun main() {
    val coffeeMachine = CoffeeMachine(
        watterSuppliesMl = 400,
        milkSuppliesMl = 540,
        coffeeBeansSuppliesGram = 120,
        disposableCupsSupplies = 9,
        totalAmountOfMoney = 550
    )
    var run = true
    coffeeMachine.readUserInput("")
    while (run) {
        run = coffeeMachine.readUserInput(readln())
    }
}


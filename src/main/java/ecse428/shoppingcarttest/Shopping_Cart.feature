Feature: Placing items in the shopping cart
    In order to purchase an item as a future order
    As a customer
    I would like to be able to place the item in the shopping cart

    Scenario Outline: Add an In-Stock item to an empty Shopping cart

    The item should be added to the shopping cart.

    # This is the NORMAL FLOW.

        Given I have an empty shopping cart
        And I am adding item <itemName>
        And I am adding quantity <itemQuantity>
        When I click "Add to Cart"
        Then I have <finalQuantity> of <itemName> in my cart

        Examples:
            | itemName                                             | itemQuantity | finalQuantity |
            | Kicking Horse Coffee, Whole Bean, Grizzly Claw, 1 lb | 1            | 1             |
            | Starbucks Coffee House Blend, Ground, 12 oz          | 1            | 1             |
            | Kicking Horse Coffee, Whole Bean, Grizzly Claw, 1 lb | 5            | 5             |
            | Starbucks Coffee House Blend, Ground, 12 oz          | 5            | 5             |

    Scenario Outline: Add an In-Stock item to a non-empty Shopping Cart

        The quantity of the selected item should be incremented to the quantity already in the shopping cart.

        # This is the ALTERNATE FLOW.

            Given I have <initialQuantity> of <itemName> in my shopping cart
            And I am adding item <itemName>
            And I am adding quantity <itemQuantity>
            When I click "Add to Cart"
            Then I have <finalQuantity> of <itemName> in my cart

            Examples:
                | itemName                                             | initialQuantity | itemQuantity | finalQuantity |
                | Kicking Horse Coffee, Whole Bean, Grizzly Claw, 1 lb | 1               | 1            | 2             |
                | Starbucks Coffee House Blend, Ground, 12 oz          | 1               | 1            | 2             |
                | Kicking Horse Coffee, Whole Bean, Grizzly Claw, 1 lb | 5               | 2            | 7             |
                | Starbucks Coffee House Blend, Ground, 12 oz          | 7               | 2            | 9             |

    Scenario Outline: Add an Out-Of-Stock item to the Shopping Cart

        The item should not be added to the shopping cart because it is out of stock.

        # This is the ERROR FLOW.

            Given I have an empty shopping cart
            And I am adding item <itemName>
            And The item has status "Out-Of-Stock"
            When I click "Add to Wishlist"
            Then I have nothing in my shopping cart

            Examples:
                | itemName                                                     | itemQuantity |
                | Sonic Mania: Collector's Edition-Nintendo Switch - Switch    | 1            |
                

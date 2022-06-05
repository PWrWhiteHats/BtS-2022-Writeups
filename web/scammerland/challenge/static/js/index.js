const validateCardNumber = number => {
  // Source: https://learnersbucket.com/examples/javascript/credit-card-validation-in-javascript/
  //Check if the number contains only numeric value  
  //and is of between 13 to 19 digits
  const regex = new RegExp("^[0-9]{13,19}$");
  if (!regex.test(number)) {
    return false;
  }

  return luhnCheck(number);
}

const luhnCheck = val => {
  let checksum = 0; // running checksum total
  let j = 1; // takes value of 1 or 2

  // Process each digit one by one starting from the last
  for (let i = val.length - 1; i >= 0; i--) {
    let calc = 0;
    // Extract the next digit and multiply by 1 or 2 on alternative digits.
    calc = Number(val.charAt(i)) * j;

    // If the result is in two digits add 1 to the checksum total
    if (calc > 9) {
      checksum = checksum + 1;
      calc = calc - 10;
    }

    // Add the units element to the checksum total
    checksum = checksum + calc;

    // Switch the value of j
    if (j == 1) {
      j = 2;
    } else {
      j = 1;
    }
  }

  //Check if it is divisible by 10 or not.
  return (checksum % 10) == 0;
}

const validate_expire_date = date => {
  date_array = date.split("/")
  today = new Date();
  expire_date = new Date();
  try {
    expire_date.setFullYear(date_array[1].trim(), date_array[0].trim(), 1);
  } catch (error) {
    return false;
  }

  if (expire_date < today) {
    return false;
  }

  return true;

}

$(function () {

  var cards = [{
    nome: "mastercard",
    colore: "#0061A8",
    src: "https://upload.wikimedia.org/wikipedia/commons/0/04/Mastercard-logo.png"
  }, {
    nome: "visa",
    colore: "#E2CB38",
    src: "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5e/Visa_Inc._logo.svg/2000px-Visa_Inc._logo.svg.png"
  }, {
    nome: "dinersclub",
    colore: "#888",
    src: "http://www.worldsultimatetravels.com/wp-content/uploads/2016/07/Diners-Club-Logo-1920x512.png"
  }, {
    nome: "americanExpress",
    colore: "#108168",
    src: "https://upload.wikimedia.org/wikipedia/commons/thumb/3/30/American_Express_logo.svg/600px-American_Express_logo.svg.png"
  }, {
    nome: "discover",
    colore: "#86B8CF",
    src: "https://lendedu.com/wp-content/uploads/2016/03/discover-it-for-students-credit-card.jpg"
  }, {
    nome: "dankort",
    colore: "#0061A8",
    src: "https://upload.wikimedia.org/wikipedia/commons/5/51/Dankort_logo.png"
  }];

  var month = 0;
  var html = document.getElementsByTagName('html')[0];
  var number = "";

  var selected_card = -1;

  $(document).click(function (e) {
    if (!$(e.target).is(".cvv") || !$(e.target).closest(".cvv").length) {
      $(".card").css("transform", "rotatey(0deg)");
      $(".seccode").css("color", "var(--text-color)");
    }
    if (!$(e.target).is(".expire") || !$(e.target).closest(".expire").length) {
      $(".date_value").css("color", "var(--text-color)");
    }
    if (!$(e.target).is(".number") || !$(e.target).closest(".number").length) {
      $(".card_number").css("color", "var(--text-color)");
    }
    if (!$(e.target).is(".inputname") || !$(e.target).closest(".inputname").length) {
      $(".fullname").css("color", "var(--text-color)");
    }
  });


  //Card number input
  $(".number").keyup(function (event) {
    $(".card_number").text($(this).val());
    number = $(this).val();

    if (parseInt(number.substring(0, 2)) > 50 && parseInt(number.substring(0, 2)) < 56) {
      selected_card = 0;
    } else if (parseInt(number.substring(0, 1)) == 4) {
      selected_card = 1;
    } else if (parseInt(number.substring(0, 2)) == 36 || parseInt(number.substring(0, 2)) == 38 || parseInt(number.substring(0, 2)) == 39) {
      selected_card = 2;
    } else if (parseInt(number.substring(0, 2)) == 34 || parseInt(number.substring(0, 2)) == 37) {
      selected_card = 3;
    } else if (parseInt(number.substring(0, 2)) == 65) {
      selected_card = 4;
    } else if (parseInt(number.substring(0, 4)) == 5019) {
      selected_card = 5;
    } else {
      selected_card = -1;
    }

    if (selected_card != -1) {
      html.setAttribute("style", "--card-color: " + cards[selected_card].colore);
      $(".bankid").attr("src", cards[selected_card].src).show();
    } else {
      html.setAttribute("style", "--card-color: #cecece");
      $(".bankid").attr("src", "").hide();
    }

    if ($(".card_number").text().length === 0) {
      $(".card_number").html("&#x25CF;&#x25CF;&#x25CF;&#x25CF; &#x25CF;&#x25CF;&#x25CF;&#x25CF; &#x25CF;&#x25CF;&#x25CF;&#x25CF; &#x25CF;&#x25CF;&#x25CF;&#x25CF;");
    }

  }).focus(function () {
    $(".card_number").css("color", "white");
  }).on("keydown input", function () {

    $(".card_number").text($(this).val());

    if (event.key >= 0 && event.key <= 9) {
      if ($(this).val().length === 4 || $(this).val().length === 9 || $(this).val().length === 14) {
        $(this).val($(this).val() + " ");
      }
    }
  })

  //Name Input
  $(".inputname").keyup(function () {
    $(".fullname").text($(this).val());
    if ($(".inputname").val().length === 0) {
      $(".fullname").text("FULL NAME");
    }
    return event.charCode;
  }).focus(function () {
    $(".fullname").css("color", "white");
  });

  //Security code Input
  $(".cvv").focus(function () {
    $(".card").css("transform", "rotatey(180deg)");
    $(".seccode").css("color", "white");
  }).keyup(function () {
    $(".seccode").text($(this).val());
    if ($(this).val().length === 0) {
      $(".seccode").html("&#x25CF;&#x25CF;&#x25CF;");
    }
  }).focusout(function () {
    $(".card").css("transform", "rotatey(0deg)");
    $(".seccode").css("color", "var(--text-color)");
  });


  //Date expire input
  $(".expire").keypress(function (event) {
    if (event.charCode >= 48 && event.charCode <= 57) {
      if ($(this).val().length === 1) {
        $(this).val($(this).val() + event.key + " / ");
      } else if ($(this).val().length === 0) {
        if (event.key == 1 || event.key == 0) {
          month = event.key;
          return event.charCode;
        } else {
          $(this).val(0 + event.key + " / ");
        }
      } else if ($(this).val().length > 2 && $(this).val().length < 9) {
        return event.charCode;
      }
    }
    return false;
  }).keyup(function (event) {
    $(".date_value").html($(this).val());
    if (event.keyCode == 8 && $(".expire").val().length == 4) {
      $(this).val(month);
    }

    if ($(this).val().length === 0) {
      $(".date_value").text("MM / YYYY");
    }
  }).keydown(function () {
    $(".date_value").html($(this).val());
  }).focus(function () {
    $(".date_value").css("color", "white");
  });

  $('.buy_button').click(function (event) {
    let card = $('#message_alert');
    card.attr('class', 'alert alert-info');
    card.hide();

    let card_number = $('.number').val();
    let owner_name = $('.inputname').val();
    let expire_date = $('.expire').val();
    let cvv = $('.cvv').val();
    if (
      $.trim(card_number) === '' || $.trim(owner_name) === '' || $.trim(expire_date) === '' || $.trim(cvv) === ''
    ) {
      card.text('Card details cannot be empty');
      card.attr('class', 'alert alert-danger mt-3');
      card.show();
      return;
    } else if (!validateCardNumber(card_number)) {
      card.text('Wrong card number');
      card.attr('class', 'alert alert-danger mt-3');
      card.show();
      return;
    } else if (!validate_expire_date(expire_date)) {
      card.text('Wrong expire date');
      card.attr('class', 'alert alert-danger mt-3');
      card.show();
      return;
    } else {
      card.text('Processing payment');
      card.attr('class', 'alert alert-info mt-3');
      card.show();
    }

    fetch('/api/pay', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        card_number: card_number,
        owner_name: owner_name,
        expire_date: expire_date,
        cvv: cvv
      }),
    }).then(
      (response) => response.json().then(
        (resp) => {
          card.attr('class', 'alert alert-danger mt-3');
          if (response.status == 200) {
            card.attr('class', 'alert alert-success mt-3');
          }
          card.text(resp.message);
          card.show();
        }
      )
    ).catch((error) => {
      card.text(error);
      card.attr('class', 'alert alert-danger mt-3');
      card.show();
    });

  });
});


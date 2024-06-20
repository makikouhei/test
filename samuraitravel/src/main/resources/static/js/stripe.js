const stripe = Stripe('pk_test_51PSF3q2KNitvjaTCDuYhaAXwQaFJfrPyDuSarpO7iJoBJsZmUyzvgYtnb5JW0hqv2CLfITWEG1Rbz5terfN5EXic003OjzWRRq');
 const paymentButton = document.querySelector('#paymentButton');
 
 paymentButton.addEventListener('click', () => {
   stripe.redirectToCheckout({
     sessionId: sessionId
   })
 });
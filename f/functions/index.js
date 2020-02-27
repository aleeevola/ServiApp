const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions

// Take the text parameter passed to this HTTP endpoint and insert it into the
// Realtime Database under the path /messages/:pushId/original
exports.addMessage = functions.https.onRequest(async (req, res) => {
  // Grab the text parameter.
  const original = req.query.text;
  // Push the new message into the Realtime Database using the Firebase Admin SDK.
  const snapshot = await admin.database().ref('/messages').push({original: original});
  // Redirect with 303 SEE OTHER to the URL of the pushed object in the Firebase console.
  res.redirect(303, snapshot.ref.toString());
});


 exports.sendNotification = functions.https.onCall((data, context) => {
    console.log("Entra a la funcion")
    const message = {
        "topic": data.idTienda,
        "data":{
          	nombreTienda: data.nombreTienda,
            tipoNotificacion: data.tipoNotificacion,
            textoNotificacion: data.textoNotificacion
        }
    };

    console.log("Envía el mensaje");
    return admin.messaging().send(message)
            .then(function(response){
                console.log('Notification sent successfully:',response);
                return 0;
            })
            .catch(function(error){
                console.log('Notification sent failed:',error);
            });
 });
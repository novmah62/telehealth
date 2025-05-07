db = db.getSiblingDB('admin');
db.createUser({
    user: "manhnv",
    pwd: "06022003",
    roles: [{ role: "root", db: "admin" }]
});


// Consultation Service
db = db.getSiblingDB("consultation_service");
// db.createUser({
//     user: "consultation_user",
//     pwd: "consultation_pass",
//     roles: [{ role: "readWrite", db: "consultation_service" }]
// });

// Audit Service
db = db.getSiblingDB("audit_service");
// db.createUser({
//     user: "audit_user",
//     pwd: "audit_pass",
//     roles: [{ role: "readWrite", db: "audit_service" }]
// });

// Notification Service
db = db.getSiblingDB("notification_service");
// db.createUser({
//     user: "notification_user",
//     pwd: "notification_pass",
//     roles: [{ role: "readWrite", db: "notification_service" }]
// });

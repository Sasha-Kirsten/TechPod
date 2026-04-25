package com.techpod.service;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

// GDPR Article 17 — Right to Erasure
// GDPR Article 20 — Right to Data Portability
// GDPR Article 15 — Right of Access
@Service 
public class GdprService {

    // TODO: Implement exportUserData(Long userId)
    //   — Return all stored data about the user as JSON (Article 20)

    // TODO: Implement anonymizeUser(Long userId)
    //   — Anonymize PII fields, keep order records for legal/accounting purposes

    // TODO: Implement scheduledDataRetentionCleanup()
    //   — Run @Scheduled nightly to anonymize users past retention date
    //   — Recommended retention: 3 years after last order or account inactivity


//     exportUserData(userId) (Article 15/20)
//     anonymizeUser(userId) (Article 17)
//     scheduled retention cleanup (@Scheduled)
     
}

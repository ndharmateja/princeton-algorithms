package q1_social_network_connectivity;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class SocialNetworkConnectivity {
    private class LogEntry {
        int member1;
        int member2;
        int timestamp;
    }

    public int findTimestamp(int n, LogEntry[] timestampsLog) {
        // Edge cases
        if (n == 0 || n == 1)
            return 0;

        // Create a union find data structure
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(n);

        // For each entry in the timestamp log
        // member ids are assumed to be 0 indexed
        for (LogEntry entry : timestampsLog) {
            int member1 = entry.member1;
            int member2 = entry.member2;
            int timestamp = entry.timestamp;

            // We connect member 1 and member 2
            uf.union(member1, member2);

            // After connecting them, if the number of sets is 1
            // it means everyone is connected and we can
            // return the timestamp
            if (uf.count() == 1)
                return timestamp;
        }

        // If we reach here it means that all the members
        // are never connected in the timeline
        // so we return -1
        return -1;
    }
}

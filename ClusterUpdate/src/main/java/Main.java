import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    record Frame(int start, int len, int cluster) {

        public boolean intersects(Frame other) {
            int thisEnd = this.start + this.len - 1;
            int otherEnd = other.start + other.len - 1;
            return (thisEnd >= other.start) && (otherEnd >= this.start);
        }

        public boolean intersects(Set<Frame> comparedSet) {
            return comparedSet.stream().anyMatch(this::intersects);
        }

        @Override
        public String toString() {
            return "Frame{" +
                    "start=" + start +
                    ", len=" + len +
                    ", cluster=" + cluster +
                    '}';
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int numOfClusters = Integer.parseInt(reader.readLine().trim());

        List<Frame> clusterFrames = new ArrayList<>(numOfClusters);
        for (int i = 0; i < numOfClusters; i++) {
            String[] input = reader.readLine().trim().split(" ");
            int start = Integer.parseInt(input[0]);
            int len = Integer.parseInt(input[1]);
            clusterFrames.add(new Frame(start, len, i));
        }

        Set<Frame> selectedFrames = new HashSet<>();
        long totalLength = 0;

        clusterFrames.sort(Comparator.comparingInt(Frame::len));

        for (Frame frame : clusterFrames) {
            if (frame.intersects(selectedFrames)) {
                if (frame.len() > totalLength) {
                    selectedFrames.clear();
                    selectedFrames.add(frame);
                    totalLength = frame.len();
                }
            } else {
                selectedFrames.add(frame);
                totalLength += frame.len();
            }
        }

        System.out.println(totalLength);

        String resultClusters = selectedFrames.stream()
                .sorted(Comparator.comparingInt(Frame::cluster))
                .map(frame -> String.valueOf(frame.cluster()))
                .collect(Collectors.joining(" "));

        System.out.println(resultClusters);

        reader.close();
    }
}
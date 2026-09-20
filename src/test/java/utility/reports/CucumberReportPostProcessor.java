package utility.reports;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CucumberReportPostProcessor {

    public static void process(Path htmlFile) throws IOException {

        String html = Files.readString(
                htmlFile,
                StandardCharsets.UTF_8
        );

        String marker = "<a>Video Location</a>";

        int searchFrom = 0;

        while (true) {

            // Find "Video Location"
            int videoLocationIndex =
                    html.indexOf(marker, searchFrom);

            if (videoLocationIndex == -1) {
                break;
            }

            // Find the end of this embedding block
            int blockEnd =
                    html.indexOf("</pre>", videoLocationIndex);

            if (blockEnd == -1) {
                break;
            }

            blockEnd += "</pre>".length();

            // Extract only this Video Location block
            String block =
                    html.substring(
                            videoLocationIndex,
                            blockEnd
                    );

            // Extract the actual Windows video path
            Pattern pathPattern = Pattern.compile(
                    "<pre[^>]*>\\s*(.*?)\\s*</pre>",
                    Pattern.DOTALL
            );

            Matcher pathMatcher =
                    pathPattern.matcher(block);

            if (!pathMatcher.find()) {
                searchFrom = blockEnd;
                continue;
            }

            String videoLocation =
                    pathMatcher.group(1).trim();

            // Make sure this is actually a video
            if (!videoLocation.toLowerCase().endsWith(".webm")
                    && !videoLocation.toLowerCase().endsWith(".mp4")) {

                searchFrom = blockEnd;
                continue;
            }

            Path videoPath;

            try {
                videoPath = Paths.get(videoLocation);
            } catch (InvalidPathException e) {

                searchFrom = blockEnd;
                continue;
            }

            if (!Files.exists(videoPath)) {

                searchFrom = blockEnd;
                continue;
            }

            // Calculate relative path from HTML report to video
            String relativeVideoPath =
                    getRelativeVideoPath(
                            htmlFile,
                            videoPath
                    );

            /*
             * Find the attachment link immediately after
             * "Video Location".
             */
            Pattern linkPattern = Pattern.compile(
                    "(<a\\s+href=\")[^\"]*(\"[^>]*>\\s*"
                            + "<span[^>]*>.*?</span>\\s*</a>)",
                    Pattern.DOTALL
            );

            Matcher linkMatcher =
                    linkPattern.matcher(block);

            if (!linkMatcher.find()) {

                searchFrom = blockEnd;
                continue;
            }

            String newLink =
                    "<a href=\""
                            + relativeVideoPath
                            + "\" target=\"_blank\" "
                            + "title=\"Open Video\">"
                            + "<span class=\"glyphicon glyphicon-play\"></span>"
                            + " Open Video"
                            + "</a>";

            String updatedBlock =
                    linkMatcher.replaceFirst(
                            Matcher.quoteReplacement(newLink)
                    );

            /*
             * Replace the old Video Location block
             * with the updated block.
             */
            html =
                    html.substring(0, videoLocationIndex)
                            + updatedBlock
                            + html.substring(blockEnd);

            /*
             * Continue searching after the modified block.
             */
            searchFrom =
                    videoLocationIndex
                            + updatedBlock.length();
        }

        Files.writeString(
                htmlFile,
                html,
                StandardCharsets.UTF_8
        );
    }

    private static String getRelativeVideoPath(
            Path htmlFile,
            Path videoPath) {

        Path htmlDirectory =
                htmlFile.toAbsolutePath()
                        .normalize()
                        .getParent();

        Path absoluteVideoPath =
                videoPath.toAbsolutePath()
                        .normalize();

        Path relativePath =
                htmlDirectory.relativize(absoluteVideoPath);

        return relativePath
                .toString()
                .replace('\\', '/');
    }
}
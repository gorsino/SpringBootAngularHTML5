/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.html5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootAngularHTML5Application {
    private final static Logger LOG = LoggerFactory.getLogger("SpringBootAngularHTML5Application");

    public static void main(String... args) {

        final String springConfigDev = (
                (args.length > 0 && args[0].compareTo("localhost") == 0) ? ",classpath:/dev.properties" : "");

        args = new String[]{
                "--spring.config.location=classpath:/application.properties" + springConfigDev
        };

        System.setProperty("spring.devtools.restart.enabled", "false");

        SpringApplication.run(SpringBootAngularHTML5Application.class, args);
    }
}

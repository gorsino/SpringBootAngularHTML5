/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.html5.api;

import com.mpalourdio.html5.config.SinglePageAppConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static java.util.List.of;

@RestController
@RequestMapping(path = SinglePageAppConfig.IGNORED_PATH)
public class ApiController {

    private final WebClient webClient;
    private final ServerProperties serverProperties;

    public ApiController(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;

        this.webClient = WebClient.create("http://localhost:"
                + serverProperties.getPort()
                + StringUtils.stripToEmpty(serverProperties.getServlet().getContextPath())
                + SinglePageAppConfig.IGNORED_PATH);
    }

    @PostMapping(path = "/fast")
    public ResponseEntity<List<String>> fast() {
        return ResponseEntity.ok(of("Hey, I am the fast response"));
    }

    @GetMapping(path = "/slow-but-reactive")
    public Mono<ResponseEntity<List<String>>> slowButReactive() {
        return webClient
                .get()
                .uri("/slow")
                .retrieve()
                .toEntityList(String.class)
                .onErrorReturn(ResponseEntity.ok(new ArrayList<>()));
    }

    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "x-requested-with")
    @GetMapping(path = "/slow")
    public ResponseEntity<List<String>> slow() throws InterruptedException {
        Thread.sleep(3000);
        return ResponseEntity.ok(of("Hey, I am the slow cross-origin response "
                + "(if performed from a port different from " + serverProperties.getPort() + ")"));
    }
}

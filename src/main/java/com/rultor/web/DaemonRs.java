/**
 * Copyright (c) 2009-2014, rultor.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the rultor.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.rultor.web;

import com.rexsl.page.PageBuilder;
import com.rultor.spi.Repo;
import com.rultor.spi.Talk;
import java.io.IOException;
import java.io.InputStream;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Single daemon.
 *
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle MultipleStringLiterals (500 lines)
 * @checkstyle ClassDataAbstractionCoupling (500 lines)
 */
@Path("/d/{repo:[0-9]+}/{name}/{hash:[0-9]+}")
public final class DaemonRs extends BaseRs {

    /**
     * Repo number.
     */
    private transient Long rnum;

    /**
     * Talk name.
     */
    private transient String name;

    /**
     * Daemon hash ID.
     */
    private transient String hash;

    /**
     * Inject it from query.
     * @param number Repo number
     */
    @PathParam("repo")
    public void setRnum(@NotNull(message = "repo number is mandatory")
        final Long number) {
        this.rnum = number;
    }

    /**
     * Inject it from query.
     * @param talk Talk name
     */
    @PathParam("name")
    public void setName(@NotNull(message = "talk name is mandatory")
        final String talk) {
        this.name = talk;
    }

    /**
     * Inject it from query.
     * @param dmn Daemon hash
     */
    @PathParam("hash")
    public void setHash(@NotNull(message = "daemon hash is mandatory")
        final String dmn) {
        this.hash = dmn;
    }

    /**
     * Get front page.
     * @return The JAX-RS response
     * @throws IOException If fails
     */
    @GET
    @Path("/")
    public Response index() throws IOException {
        final Talk talk = this.user().repos()
            .get(this.rnum).talks().get(this.name);
        return new PageBuilder()
            .stylesheet("/xsl/daemon.xsl")
            .build(EmptyPage.class)
            .init(this)
            .append(new Breadcrumbs().with("self", "home").bundle())
            .render()
            .build();
    }

    /**
     * Get stream.
     * @return The JAX-RS response
     */
    @GET
    @Path("/stream")
    @Produces(MediaType.TEXT_PLAIN)
    public InputStream stream() {
        final Repo repo = this.user().repos().get(this.rnum);
        return null;
    }

}

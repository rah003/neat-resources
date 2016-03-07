/**
 *
 * Copyright 2016 by Jan Haderka <jan.haderka@neatresults.com>
 *
 * This file is part of neat-tweaks module.
 *
 * Neat-tweaks is free software: you can redistribute
 * it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * Neat-tweaks is distributed in the hope that it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with neat-tweaks.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @license GPL-3.0 <http://www.gnu.org/licenses/gpl.txt>
 *
 * Should you require distribution under alternative license in order to
 * use neat-tweaks commercially, please contact owner at the address above.
 *
 */
package com.neatresults.mgnltweaks.resources;

import info.magnolia.module.resources.ResourceLinker;
import info.magnolia.resourceloader.Resource;
import info.magnolia.resourceloader.ResourceOrigin;
import info.magnolia.resourceloader.ResourceVisitor;
import info.magnolia.resourceloader.layered.LayeredResource;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

/**
 * Templating Functions to expose json builder.
 */
public class ResourcesTemplatingFunctions {

    private ResourceOrigin<LayeredResource> origin;
    private ResourceLinker linker;

    @Inject
    public ResourcesTemplatingFunctions(ResourceOrigin<LayeredResource> resourceOrigin, ResourceLinker linker) {
        this.origin = resourceOrigin;
        this.linker = linker;
    }

    /**
     * Generates css links for all css matching provided pattern(s).
     */
    public String css(String pattern) {
        return css(Arrays.asList(new String[] { pattern }));
    }

    /**
     * Generates css links for all css matching provided pattern(s).
     */
    public String css(String pattern, String otherAtteributes) {
        return css(Arrays.asList(new String[] { pattern }), otherAtteributes);
    }

    /**
     * Generates css links for all css matching provided pattern(s).
     */
    public String css(final List<String> patterns) {
        return css(patterns, null);
    }

    /**
     * Generates css links for all css matching provided pattern(s).
     */
    public String css(List<String> patterns, String otherAtteributes) {
        final String prefix = "<link rel=\"stylesheet\" type=\"text/css\" " + (otherAtteributes == null ? "" : otherAtteributes) + " href=\"";
        final String suffix = "\"/>";
        return generate(patterns, prefix, suffix, false);
    }

    /**
     * Generates cached css links for all css matching provided pattern(s).
     */
    public String cachedCss(String pattern) {
        return cachedCss(Arrays.asList(new String[] { pattern }));
    }

    /**
     * Generates cached css links for all css matching provided pattern(s).
     */
    public String cachedCss(String pattern, String otherAtteributes) {
        return cachedCss(Arrays.asList(new String[] { pattern }), otherAtteributes);
    }

    /**
     * Generates cached css links for all css matching provided pattern(s).
     */
    public String cachedCss(final List<String> patterns) {
        return cachedCss(patterns, null);
    }

    /**
     * Generates cached css links for all css matching provided pattern(s).
     */
    public String cachedCss(List<String> patterns, String otherAtteributes) {
        final String prefix = "<link rel=\"stylesheet\" type=\"text/css\" " + (otherAtteributes == null ? "" : otherAtteributes) + " href=\"";
        final String suffix = "\"/>";
        return generate(patterns, prefix, suffix, true);
    }

    /**
     * Generates js links for all js matching provided pattern(s).
     */
    public String js(String pattern) {
        return js(Arrays.asList(new String[] { pattern }));
    }

    /**
     * Generates js links for all js matching provided pattern(s).
     */
    public String js(final List<String> patterns) {
        final String prefix = "<script src=\"";
        final String suffix = "\"></script>";
        return generate(patterns, prefix, suffix, false);
    }

    /**
     * Generates cached js links for all js matching provided pattern(s).
     */
    public String cachedJs(String pattern) {
        return cachedJs(Arrays.asList(new String[] { pattern }));
    }

    /**
     * Generates cached js links for all js matching provided pattern(s).
     */
    public String cachedJs(final List<String> patterns) {
        final String prefix = "<script src=\"";
        final String suffix = "\"></script>";
        return generate(patterns, prefix, suffix, true);
    }

    private String generate(List<String> patterns, String prefix, String suffix, final boolean isCached) {
        List<String> results = new LinkedList<>();
        origin.traverseWith(new ResourceVisitor() {

            @Override
            public void visitFile(final Resource resource) {
                if (patterns.parallelStream().anyMatch(pattern -> matchSafely(resource.getPath(), pattern))) {
                    results.add(prefix + linker.linkTo(resource.getPath(), isCached) + suffix);
                }
            }

            private boolean matchSafely(String resource, String pattern) {
                try {
                    return resource.matches(pattern);
                } catch (PatternSyntaxException e) {
                    // ignore
                }
                return false;
            }

            @Override
            public boolean visitDirectory(Resource resource) {
                return true;
            }
        });
        return StringUtils.join(results, "\n");
    }
}
